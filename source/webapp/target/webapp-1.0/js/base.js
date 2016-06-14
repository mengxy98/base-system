/** TODO:项目正式上线时，请删除此处的检测*/
(function () {
    var s = document.createElement("script");
    s.onload = function () {
        bootlint.showLintReportForCurrentDocument([]);
    };
    s.src = "js/bootlint.js";
    document.body.appendChild(s)
})();
/*******************************/
