<!DOCTYPE html>
<html>
<head>
    <title>Latest</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<h2>Codes</h2>
<#list listCode as codes>
<div style = "border : red solid 2px;margin : 4px;padding : 10px;">
<span id="load_date"> ${codes.getDate()}
</span>
    <pre id="code_snippet" style = "border : black solid 2px;margin : 4px;padding : 10px;"> ${codes.getCode()}
</pre>
    </div>
</#list>
</body>
</html>