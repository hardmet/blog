<%--
  Created by IntelliJ IDEA.
  User: boris_azanov
  Date: 25.12.16
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="js-upload-image-panel">
    <div class="js-result-block">
        <p></p>
    </div>
    <!-- Upload file form -->
    <form class="js-upload-image">
        <label>Upload your file:</label>
        <input class="js-file-name" type="text" title="Файлы на загрузку"/>
        <input class="js-upload-image-input" type="file" name="uploadImages" accept="image/*"
               ${param.isMultiple ? 'multiple' : ''} hidden data-type="${param.type}"/>
        <progress class="js-progress progress-bar" value="0" max="100"></progress>
        <button class="js-submit">Submit</button>
    </form>
</div>