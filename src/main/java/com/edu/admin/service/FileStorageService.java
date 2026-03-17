package com.edu.admin.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.edu.admin.model.common.FileMeta;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.edu.admin.exception.FileStorageException;
import com.edu.admin.exception.FileNotFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;

@Service
public class FileStorageService {

    private static final String IMAGE_PNG_FORMAT = "png";

    @Value("${upload.path}")
    private String path;

    private Path fileStorageLocation;


    public FileMeta storeFile(MultipartFile file) {
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();

        try {
            // directory 해당 경로까지 디렉토리를 모두 만든다.
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        // 파일명을 바르게 수정한다.
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String mimeType = file.getContentType();
        Long fileSize = file.getSize();
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        String baseName = FilenameUtils.getBaseName(fileName);
        String thumbFileName = baseName.concat(".png");
        //String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/common/filepreview/").path(fileName).toUriString();
        //String thumbUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/common/filepreview/").path(thumbFileName).toUriString();
        String fileDownloadUri = "/common/filepreview/".concat(fileName);
        String thumbUri = "/common/filepreview/".concat(thumbFileName);

        try {
            // 파일명에 '..' 문자가 들어 있다면 오류를 발생하고 아니라면 진행(해킹및 오류방지)
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // 파일을 저장할 경로를 Path 객체로 받는다.
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            //썸네일 생성
            Path thumbLocation = this.fileStorageLocation.resolve(thumbFileName);
            File video = new File(targetLocation.toUri());
            File newFile = new File(thumbLocation.toUri());
            getThumbnail(video, newFile, 800);
            //imgThumbnail.createNewFile();

        } catch (IOException | JCodecException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        FileMeta fileMeta = new FileMeta();
        fileMeta.setFileName(baseName);
        fileMeta.setFileSize(fileSize);
        fileMeta.setMimeType(mimeType);
        fileMeta.setFileUrl(fileDownloadUri);
        fileMeta.setThumbnailUrl(thumbUri);
        fileMeta.setExtension(extension);

        return fileMeta;

    }

    public void storeFileDelete(String fileName) {
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();

        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try {
            File file = new File(targetLocation.toUri());

            if (file.exists()) {
                file.delete();
            }

        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    public String storeBase64File(String base64file) {
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();
        String fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(12), IMAGE_PNG_FORMAT);
        String fileDownloadUri = "/common/img/".concat(fileName);
        try {
            // directory 해당 경로까지 디렉토리를 모두 만든다.
            Files.createDirectories(this.fileStorageLocation);
            String data = base64file.split(",")[1];
            byte decodedImg[] = Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8));
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            try  {
                BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(decodedImg));
                bufImg = resize(bufImg, 800);
                ImageIO.write(bufImg, IMAGE_PNG_FORMAT, new File(targetLocation.toUri()));

            } catch (Exception e ) {
                System.err.println("Couldn't write to file...");
            }


        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        return fileDownloadUri;

    }

    /**
     * @param source    mp4 file.
     * @param thumbnail
     * @return
     * @throws IOException
     * @throws JCodecException
     */
    public File getThumbnail(File source, File thumbnail, int size) throws IOException, JCodecException {

        int frameNumber = 0;

        Picture picture = FrameGrab.getFrameFromFile(source, frameNumber);
        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);


        bufferedImage = resize(bufferedImage, size);

        ImageIO.write(bufferedImage, IMAGE_PNG_FORMAT, thumbnail);
        return thumbnail;
    }

    public static byte[] scale(byte[] fileData, int width, int height) {

        ByteArrayInputStream in = new ByteArrayInputStream(fileData);

        try {
            BufferedImage img = ImageIO.read(in);
            if (height == 0) {
                height = (width * img.getHeight()) / img.getWidth();
            }
            if (width == 0) {
                width = (height * img.getWidth()) / img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, IMAGE_PNG_FORMAT, buffer);

            return buffer.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Takes a BufferedImage and resizes it according to the provided targetSize
     *
     * @param src        the source BufferedImage
     * @param targetSize maximum height (if portrait) or width (if landscape)
     * @return a resized version of the provided BufferedImage
     */
    private BufferedImage resize(BufferedImage src, int targetSize) {
        if (targetSize <= 0) {
            return src; //this can't be resized
        }
        int targetWidth = targetSize;
        int targetHeight = targetSize;
        float ratio = ((float) src.getHeight() / (float) src.getWidth());
        if (ratio <= 1) { //square or landscape-oriented image
            targetHeight = (int) Math.ceil((float) targetWidth * ratio);
        } else { //portrait image
            targetWidth = Math.round((float) targetHeight / ratio);
        }
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
        g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return bi;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static byte[] resize(Image image, byte[] fileData, int scaledWidth, int scaledHeight) {
        // 원본 이미지 사이즈 가져오기
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        double ratio = (double) scaledWidth / (double) scaledHeight;
        int w = (int) (imageWidth * ratio);
        int h = (int) (imageHeight * ratio);

        byte[] bytes = scale(fileData, w, h);

        return bytes;
    }
}