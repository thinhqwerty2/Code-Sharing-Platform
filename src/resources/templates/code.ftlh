<!DOCTYPE html>
<html lang="en">
<head>
    <title>Code</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<span id="load_date">${code_snippet.getDate()}
</span>
<br>
<#if code_snippet.getTime() gt 0 >
    <span id="time_restriction">The code will be available for ${code_snippet.getTime()}
</span>
</#if>
<br>
<#if code_snippet.getViews() gt 0>
    <span id="views_restriction">${code_snippet.getViews()} more views allowed
</span>
    <#elseif restrictView>
        <span id="views_restriction">${code_snippet.getViews()} more views allowed
</span>

</#if>


<pre id="code_snippet"> <code id="code">
    ${code_snippet.getCode()}
    </code>
</pre>

</body>
</html>