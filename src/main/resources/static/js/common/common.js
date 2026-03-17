if (!this.COMMON) {
    COMMON = {};
}

(function() {
    "use strict";

    var defaultObject = {
        errorMessage: '요청하신 업무에 문제가 발생하여 처리 되지 못했습니다.',
        type: "GET"
    };

    COMMON.dateRangeConfig = function() {
        $.datepicker.setDefaults({
            dateFormat: 'yy-mm-dd',
            prevText: '이전 달',
            nextText: '다음 달',
            monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            dayNames: ['일', '월', '화', '수', '목', '금', '토'],
            dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
            showMonthAfterYear: true,
            yearSuffix: '년'
        });
    }
    COMMON.dateRangePicker = function(startDate, endDate) {

        // Date Range Picker
        $(startDate).datepicker({
            defaultDate: "-30d",
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 2,
            prevText: '<i class="fa fa-chevron-left"></i>',
            nextText: '<i class="fa fa-chevron-right"></i>',
            onClose: function (selectedDate) {
                $(endDate).datepicker("option", "minDate", selectedDate);
            }

        }).datepicker("setDate", -30);

        $(endDate).datepicker({
            defaultDate: "+1w",
            changeMonth: true,
            changeYear: true,
            numberOfMonths: 2,
            prevText: '<i class="fa fa-chevron-left"></i>',
            nextText: '<i class="fa fa-chevron-right"></i>',
            onClose: function (selectedDate) {
                $(startDate).datepicker("option", "maxDate", selectedDate);
            }
        }).datepicker("setDate", 1);
    }
    COMMON.tempKey =  function () {
        // Math.random should be unique because of its seeding algorithm.
        // Convert it to base 36 (numbers + letters), and grab the first 9 characters
        // after the decimal.
        return '_' + Math.random().toString(36).substr(2, 9);
    }
    COMMON.ajaxSyncJson = function(param) {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        jQuery.ajax({
            url: param.url,
            type: param.type,
            dataType: "json",
            data: param.data,
            contentType: COMMON.util.isEmpty(param.contentType) ? "application/json" : param.contentType,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            async: true,
            cache: false,
            success: param.success,
            error: function(errorResult) {
                alert(errorResult.errmsg);
            }
        });

    },
    COMMON.ajaxAsyncJson = function(param) {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        jQuery.ajax({
            url: param.url,
            type: param.type,
            dataType: "json",
            data: param.data,
            contentType: COMMON.util.isEmpty(param.contentType) ? "application/json" : param.contentType,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            async: false,
            cache: false,
            success: param.success,
            error: function(errorResult) {
                alert(errorResult.errmsg);
            }
        });
    },
    COMMON.Date = function(value, separator) {
        var arr = [];

        if (!value) {
            return "";
        }

        separator = separator ? (typeof separator === "string" ? separator : "-") : "-";

        if (value.length === 8) {
            arr.push(value.substr(0, 4));
            arr.push(value.substr(4, 2));
            arr.push(value.substr(6, 2));

            return arr.join(separator);
        }

        return value;
    },
    COMMON.getDate = function (separator) {
        var date = new Date(),
            year = date.getFullYear(),
            month = date.getMonth() + 1,
            day = date.getDate();

        return [year.toString(), COMMON.zeroPlus(month), COMMON.zeroPlus(day)].join(separator);
    },
    COMMON.toJavaScriptDate = function(value) {
        var pattern,
            results,
            dt;

        if (!value) {
            return "";
        }

        pattern = /Date\(([^)]+)\)/;
        results = pattern.exec(value);
        dt = new Date(parseFloat(results[1]));

        return dt;
    },
    COMMON.jsonDateFormat = function (value) {
        var arr = [],
            date;

        if (!value) {
            return "";
        }

        date = COMMON.toJavaScriptDate(value);

        arr.push(date.getFullYear());
        arr.push(COMMON.zeroPlus(date.getMonth() + 1));
        arr.push(COMMON.zeroPlus(date.getDate()));

        return arr.join("-");
    },
    COMMON.jsonDateTimeFormat = function (value) {
        var dateArray = [],
            timeArray = [],
            date;

        if (!value) {
            return "";
        }

        date = COMMON.toJavaScriptDate(value);

        dateArray.push(date.getFullYear());
        dateArray.push(COMMON.zeroPlus(date.getMonth() + 1));
        dateArray.push(COMMON.zeroPlus(date.getDate()));

        timeArray.push(COMMON.zeroPlus(date.getHours()));
        timeArray.push(COMMON.zeroPlus(date.getMinutes()));
        timeArray.push(COMMON.zeroPlus(date.getSeconds()));

        return dateArray.join("-") + " " + timeArray.join(":");
    },
    COMMON.zeroPlus = function(value) {
        return value < 10 ? "0" + value.toString() : value.toString();
    },
    COMMON.loadCss = function(cssName, callback) {
        var body = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');

        COMMON.cssRemove(cssName);

        link.href = cssName;
        link.rel = 'stylesheet';
        link.type = 'text/css';

        link.onload = callback;
        body.appendChild(link);
    },
    COMMON.loadScript = function(scriptName, callback) {
        var body = document.getElementsByTagName('body')[0];
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = scriptName;

        script.onload = callback;
        body.appendChild(script);
    },
    COMMON.cssRemove = function(cssName) {
        var links = document.getElementsByTagName('link');

        for (var i = 0; i < links.length; i++) {
            if (links[i].getAttribute("href") === cssName) {
                links[i].remove();

                break;
            }
        }
    },
    COMMON.scriptRemove = function(scriptName) {
        var links = document.getElementsByTagName('script');

        for (var i = 0; i < links.length; i++) {
            if (links[i].getAttribute("src") === scriptName) {
                links[i].remove();

                break;
            }
        }
    },
    COMMON.numberWithCommas = function(value) {
        return value.toString().replace(/[^0-9]/g, "").replace(/,/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    COMMON.replaceAll = function(value, searchValue) {
        var rex = new RegExp('' + searchValue + '', 'g');
        return value.replace(rex, "");
    },
    COMMON.restPath = function(obj) {
        var restPathArray = [];

        for (var item in obj) {
            restPathArray.push(obj[item]);
        }

        return restPathArray.join("/");
    },
    COMMON.byteLength = function(s, b, i, c) {
        for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 2 : c >> 7 ? 2 : 1);

        return b;
    },
    COMMON.getParameter = function(name) {
        var rtnval = '',
            nowAddress = unescape(location.href),
            parameters = (nowAddress.slice(nowAddress.indexOf('?') + 1, nowAddress.length)).split('&'),
            i;

        for (i = 0; i < parameters.length; i++) {
            var varName = parameters[i].split('=')[0];
            if (varName.toUpperCase() == name.toUpperCase()) {
                rtnval = parameters[i].split('=')[1];
                break;
            }
        }

        return rtnval;
    },
    COMMON.optionString = function(data, selectedValue, defaultOption) {
        var option = defaultOption ? '<option value="">' + defaultOption + '</option>' : "",
            item;

        for (item in data) {
            option += '<option value="' + data[item].value + '"' + (selectedValue === data[item].value ? " selected" : "") + '>' + data[item].text + '</option>';
        }

        return option;
    },
    COMMON.browser = function() {
        var agent = navigator.userAgent.toLowerCase();

        if ((navigator.appName === 'Netscape' && navigator.userAgent.search('Trident') !== -1) || agent.indexOf("msie") !== -1) {
            return "ie";
        }

        if (agent.indexOf("chrome") !== -1) {

            return "chrome";
        }

        if (agent.indexOf("safari") !== -1) {

            return "safari";

        }

        if (agent.indexOf("firefox") !== -1) {

            return "firefox";
        }

        return "";
    },

    COMMON.util = function(){

        var o = {};

        o.isNull = function(val) {
            return _.isUndefined(val) || _.isNull(val);
        };

        //StringUtil
        //주어진 문자열이 null 또는 공백일 경우 참 반환
        o.isEmpty = function(s) {
            if (!_.isString(s)) return false;
            if (s == null || s === '') {
                return true;
            }
            return false;
        };

        //입력된 문자열이 숫자와 알파벳로만 구성되어있는지 체크
        o.isAlphaNumeric = function(s) {
            if (!_.isString(s)) return false;
            return /^[A-Za-z0-9]+$/.test(s);
        };

        //입력된 문자열이 숫자로만 구성되어있는지 체크
        o.isNumeric = function(s) {
            if (!_.isString(s)) return false;
            return /^[0-9]+$/.test(s);
        };

        //입력된 문자열이 정수로만 구성되어있는지 체크
        o.isInteger = function(s) {
            if (!_.isString(s)) return false;
            return /^[-|+]?\d+$/.test(s);
        };

        //입력된 문자열이 알파벳로만 구성되어있는지 체크
        o.isAlpha = function(s) {
            if (!_.isString(s)) return false;
            return /^[A-Za-z]+$/.test(s);
        };

        //입력된 문자열이 한글로만 구성되어 있는지 체크
        o.isHangul = function(s) {
            if (!_.isString(s)) return false;
            return /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+$/.test(s);
        };

        //해당하는 문자열에 대한 길이 반환
        o.getLength = function(s) {
            if (!_.isString(s)) return 0;
            return s.length;
        };

        //해당하는 문자열에 대해서 byte 단위에 대해서 길이 계산해서 총 길이 반환
        //한글은 3Byte
        o.getByteLength = function(s) {
            if (!_.isString(s)) return 0;
            var b, i, c = 0;
            for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
            return b;
        };

        //문자열의 왼쪽의 공백 문자열 제거
        o.leftTrim = function(s) {
            if (!_.isString(s)) return '';
            return s.replace(/^\s+/, "");
        };

        //문자열의 오른쪽의 공백 문자열 제거
        o.rightTrim = function(s) {
            if (!_.isString(s)) return '';
            return s.replace(/\s+$/, "");
        };

        //문자열의 공백 문자열 제거
        o.trim = function(s) {
            if (!_.isString(s)) return '';
            return s.replace(/^\s+|\s+$/g, "");
        };

        //해당하는 문자열에 대해서 입력된 길이만큼 부족한 길이를 왼쪽부터 공백으로 채워넣는다.
        o.leftPad = function(s, len, c) {
            if (!_.isString(s) || !_.isString(c)) return '';
            if (!_.isNumber(len) || len <= o.getLength(s)) return s;
            if (o.getLength(c) != 1) return s;

            var padLen = len - o.getLength(s);
            for (var i = 0; i < padLen; i++) {
                s = c + s;
            }
            return s;
        };

        //해당하는 문자열에 대해서 입력된 길이만큼 부족한 길이를 오른쪽부터 지정된 문자로 채워넣는다.
        o.rightPad = function(s, len, c) {
            if (!_.isString(s) || !_.isString(c)) return '';
            if (!_.isNumber(len) || len <= o.getLength(s)) return s;
            if (o.getLength(c) != 1) return s;

            var padLen = len - o.getLength(s);
            for (var i = 0; i < padLen; i++) {
                s += c;
            }
            return s;
        };

        o.addCommas = function(s) {
            if (_.isNumber(s)) s = '' + s;
            if (!_.isString(s)) return '';

            var x, x1, x2 = '';
            x = s.split('.');
            x1 = x[0];
            x2 = x.length > 1 ? '.' + x[1] : '';
            var rgx = /(\d+)(\d{3})/;
            while (rgx.test(x1)) {
                x1 = x1.replace(rgx, '$1' + ',' + '$2');
            }
            return x1 + x2;
        };

        //입력된 문자열이 주어진 문자열과 일치하는 모든 문자열을 바꿔야할 문자열로 변경
        o.replaceAll = function(s, bs, as) {
            if (!_.isString(s) || !_.isString(bs) || !_.isString(as)) return '';
            return s.split(bs).join(as);
        };

        //HTML tag가 들어있는 문자열에 대해 unescape해준다.
        o.replaceHtmlEscape = function(s) {
            if (!_.isString(s)) return '';
            return _.escape(s);
        };

        //unescaped된 문자열에 대해 HTML tag 형태로 바꿔준다.
        o.removeEscapeChar = function(s) {
            if (!_.isString(s)) return '';
            return _.unescape(s);
        };

        //DateUtil
        //입력된 일자가 유효한 일자인지 체크
        o.isDate = function(s) {
            s = s.replace(/-/gi, '');
            if (!_.isString(s) || o.isEmpty(s) || o.getLength(s) != 8) return false;

            var year = Number(s.substring(0, 4));
            var month = Number(s.substring(4, 6));
            var day = Number(s.substring(6, 8));

            if (1 > month || 12 < month) {
                return false;
            }

            var lastDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
            var lastDay = lastDays[month - 1];

            if (month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)) {
                lastDay = 29;
            }

            if (1 > day || lastDay < day) {
                return false;
            }

            return true;
        };

        //입력된 시간이 유효한지 체크
        o.isTime = function(s) {
            if (!_.isString(s) || o.isEmpty(s) || o.getLength(s) != 6) return false;

            var h = Number(s.substring(0, 2));
            var m = Number(s.substring(2, 4));
            var s = Number(s.substring(4, 6));

            if (0 > h || 23 < h) {
                return false;
            }

            if (0 > m || 59 < m) {
                return false;
            }

            if (0 > s || 59 < s) {
                return false;
            }

            return true;
        };

        //입력된 시간이 유효한지 체크
        o.isHour = function(s) {
            if (!_.isString(s) || o.isEmpty(s) || o.getLength(s) != 2) return false;

            var h = Number(s);

            if (0 > h || 23 < h) {
                return false;
            }

            return true;
        };

        //입력받은 일자를 Date형으로 변환
        o.strToDate = function(s) {
            if (!_.isString(s)) return null;

            var array = s.split(' ');
            var date = array[0];
            var time = '000000';

            if (2 == array.length) {
                time = array[1];
            }

            if (!o.isDate(date)) return null;
            if (!o.isTime(time)) return null;

            var year = date.substring(0, 4);
            var month = Number(date.substring(4, 6)) - 1;
            var day = date.substring(6, 8);
            var hour = time.substring(0, 2);
            var minute = time.substring(2, 4);
            var second = time.substring(4, 6);

            return new Date(year, o.leftPad('' + month, 2, '0'), day, hour, minute, second);
        };

        o.formatDate = function formatDate(d, f) {
            if (!_.isString(f)) return '';

            if (_.isDate(d)) {
                return f.replace(/(yyyy|yy|mm|dd|hh24|hh|mi|ss|fff|a\/p)/gi, function($1) {
                    switch ($1) {
                        case "yyyy":
                            return '' + d.getFullYear();
                        case "yy":
                            return o.leftPad('' + (d.getFullYear() % 1000), 4, '0').substring(2, 4);
                        case "mm":
                            return o.leftPad('' + (d.getMonth() + 1), 2, '0');
                        case "dd":
                            return o.leftPad('' + d.getDate(), 2, '0');
                        case "hh24":
                            return o.leftPad('' + d.getHours(), 2, '0');
                        case "hh":
                            return o.leftPad('' + ((h = d.getHours() % 12) ? h : 12), 2, '0');
                        case "mi":
                            return o.leftPad('' + d.getMinutes(), 2, '0');
                        case "ss":
                            return o.leftPad('' + d.getSeconds(), 2, '0');
                        case "fff":
                            return o.leftPad('' + d.getMilliseconds(), 3, '0');
                        case "a/p":
                            return d.getHours() < 12 ? "오전" : "오후";
                        default:
                            return $1;
                    }
                });
            } else if (_.isString(d)) {
                return formatDate(o.strToDate(d), f);
            }

            return '';
        };

        //입력받은 일자의 요일 반환
        o.getDayOfWeek = function(s) {
            if (!o.isDate(s)) return '';
            var week = ['일', '월', '화', '수', '목', '금', '토'];
            return week[o.strToDate(s).getDay()];
        };

        //입력받은 두 날짜 사이의 일자 계산
        o.getDay = function(sd, ed) {
            if (!o.isDate(sd) || !o.isDate(ed)) return -1;
            if (Number(ed) < Number(sd)) return -2;

            var newSd = o.strToDate(sd);
            var newEd = o.strToDate(ed);
            var diffTime = newEd.getTime() - newSd.getTime();

            return Math.floor(diffTime / (1000 * 60 * 60 * 24));
        };

        //입력받은 일자에 대해서 해당 일만큼 더한 일자 반환. 마이너스 일자는 입력받은 일자보다 이전의 일자로 계산해서 반환
        o.addDays = function(s, d, f) {
            if (!o.isDate(s) || !_.isNumber(d)) return '';
            var newDt = o.strToDate(s);
            newDt.setDate(newDt.getDate() + (d));
            return o.formatDate(newDt, f || 'yyyymmdd');
        };

        //입력받은 일자에 대해서 해당 개월수만큼 더한 일자 반환. 마이너스 개월수는 입력받은 일자보다 이전 일자로 계산해서 반환
        o.addMonths = function(s, m, f) {
            if (!o.isDate(s) || !_.isNumber(m)) return '';
            var newDt = o.strToDate(s);
            newDt.setMonth(newDt.getMonth() + (m));
            return o.formatDate(newDt, f || 'yyyymmdd');
        };

        //입력받은 일자에 대해서 해당 년수만큼 더한 일자 반환. 마이너스 년수는 입력받은 일자보다 이전 일자로 계산해서 반환
        o.addYears = function(s, y, f) {
            if (!o.isDate(s) || !_.isNumber(y)) return '';
            var newDt = o.strToDate(s);
            newDt.setFullYear(newDt.getFullYear() + (y));
            return o.formatDate(newDt, f || 'yyyymmdd');
        };

        //입력받은 일자에 마지막 일 반환
        o.getLastDay = function(s, f) {
            if (!o.isDate(s)) return '';
            var newDt = o.strToDate(s);
            newDt.setMonth(newDt.getMonth() + 1);
            newDt.setDate(0);
            return o.formatDate(newDt, f || 'yyyymmdd');
        };

        o.checkDateOver = function(s, f) {
            if (!o.isDate(s)) return '';

            if(s > f){
                return false;
            }

            return true;
        };

        //NumberUtil
        o.strToInt = function(s) {
            if (!_.isString(s)) return 0;
            return parseInt(s, 10);
        };

        o.parseInt = function(s) {
            return parseInt(s, 10);
        };

        //ValidationUtil
        //문자열의 길이가 최소, 최대 길이 사이에 존재하는지 체크
        o.isRangeLength = function(s, min, max) {
            if (!_.isString(s) || !_.isNumber(min) || !_.isNumber(max)) return false;

            var len = o.getLength(s);
            if (min <= len && len <= max) {
                return true;
            }

            return false;
        };

        //문자열의 길이가 byte 단위로 계산했을때 최소, 최대 길이 사이에 존재하는지 체크
        o.isRangeByteLength = function(s, min, max) {
            if (!_.isString(s) || !_.isNumber(min) || !_.isNumber(max)) return false;

            var len = o.getByteLength(s);
            if (min <= len && len <= max) {
                return true;
            }

            return false;
        };

        //입력된 이메일주소가 유효한이메일주소인지 검증한다.
        o.isEmail = function(s) {
            if (!_.isString(s)) return false;
            return /^([0-9a-zA-Z]+)([0-9a-zA-Z\._-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,3}$/.test(s);
            //return /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/.test(s);
        };

        o.phoneFormat = function(val) {
            if (o.isNull(val)) {
                return "";
            }

            val = o.replaceAll(val, "-", "");

            if(val.length == 12){
                var val = val.substring(0,4)+'-'+val.substring(4,8)+'-'+val.substring(8,12);
                return val;
            }

            return val.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
        };

        o.formatFileSize = function (bytes) {
            if (typeof bytes !== 'number') {
                return '';
            }
            if (bytes >= 1000000000) {
                return (bytes / 1000000000).toFixed(2) + ' GB';
            }
            if (bytes >= 1000000) {
                return (bytes / 1000000).toFixed(2) + ' MB';
            }
            return (bytes / 1000).toFixed(2) + ' KB';
        };

        return o;

    }();
}());

