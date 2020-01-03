package net.silica.readView.config;

import android.app.Activity;
import android.graphics.Point;

import net.silica.sessionApp.SessionManager;


public class ModeUtils {
    private  String PREF_NAME = "cssStyle";
    SettingMode settingMode;
    SessionManager sessionManager;
    String contentsCSS;
    String contentsJS;
    String contentHTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"page\"></div>\n" +
            "<div id=\"blankpage\"></div>\n" +
            "</body>\n" +
            "</html>\n";
    String pathfileHtml;

    public ModeUtils(Activity mActivity) {
        sessionManager = new SessionManager(mActivity);
        settingMode=new SettingMode();
        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getRealSize(size);
        int width = size.x;
        int height = (int) (size.y / mActivity.getResources().getDisplayMetrics().density);
        settingMode.setWidth(width);
        settingMode.setHeight(height);
    }

    public SettingMode getSettingMode() {
        String fontFamily = sessionManager.getReaded("font-family");
        if (fontFamily.equals("")) fontFamily = "UTM Daxline";
        settingMode.setFontFamily(fontFamily);
        String fontWeight = sessionManager.getReaded("font-weight");
        if (fontWeight.equals("")) fontWeight = "normal";
        settingMode.setFontWeight(fontWeight);
        String fontStyle = sessionManager.getReaded("font-style");
        if (fontStyle.equals("")) fontStyle = "normal";
        settingMode.setFontStyle(fontStyle);
        String bgColor = sessionManager.getReaded("background-color");
        if (bgColor.equals("")) bgColor = "white";
        settingMode.setBgColor(bgColor);
        String color = sessionManager.getReaded("color");
        if (color.equals("")) color = "black";
        settingMode.setTextColor(color);
        String fontSize = sessionManager.getReaded("font-size");
        if (fontSize.equals(""))
            settingMode.setfontSize(125);
        else settingMode.setfontSize(Integer.parseInt(fontSize));
        String paddingTB = sessionManager.getReaded("paddingTopBottom");
        if (paddingTB.equals(""))
            settingMode.setPaddingTopBottom(20);
        else settingMode.setPaddingTopBottom(Integer.parseInt(paddingTB));
        String paddingLR = sessionManager.getReaded("paddingLeftRigh");
        if (paddingLR.equals(""))
            settingMode.setPaddingLeftRight(20);
        else settingMode.setPaddingLeftRight(Integer.parseInt(paddingLR));
        String columnGap = sessionManager.getReaded("column-gap");
        if (columnGap.equals(""))
            settingMode.setColumnGap(40);
        else settingMode.setColumnGap(Integer.parseInt(columnGap));

        return settingMode;
    }

    public void setSettingMode(SettingMode settingMode) {
        sessionManager.setReading("font-family", settingMode.getFontFamily());
        sessionManager.setReading("font-weight", settingMode.getFontWeight());
        sessionManager.setReading("font-style", settingMode.getFontStyle());
        sessionManager.setReading("background-color", settingMode.getBgColor());
        sessionManager.setReading("color", settingMode.getTextColor());
        sessionManager.setReading("font-size", String.valueOf(settingMode.getfontSize()));
        sessionManager.setReading("paddingTopBottom", String.valueOf(settingMode.paddingTopBottom));
        sessionManager.setReading("paddingLeftRigh", String.valueOf(settingMode.paddingLeftRight));
        sessionManager.setReading("column-gap", String.valueOf(settingMode.getColumnGap()));
        this.settingMode = settingMode;
    }

    public SettingMode getDefaultSettingMode() {
        if (sessionManager.getReaded("font-family").equals(""))
            settingMode.setFontFamily("UTM Daxline");
        if (sessionManager.getReaded("font-weight").equals("")) settingMode.setFontWeight("normal");
        if (sessionManager.getReaded("font-style").equals("")) settingMode.setFontStyle("normal");
        if (sessionManager.getReaded("background-color").equals(""))
            settingMode.setBgColor("white");
        if (sessionManager.getReaded("color").equals("")) settingMode.setTextColor("black");
        if (sessionManager.getReaded("font-size").equals("")) settingMode.setfontSize(125);
        if (sessionManager.getReaded("paddingTopBottom").equals(""))
            settingMode.setPaddingTopBottom(20);
        if (sessionManager.getReaded("paddingLeftRigh").equals(""))
            settingMode.setPaddingLeftRight(20);
        if (sessionManager.getReaded("column-gap").equals("")) settingMode.setColumnGap(40);
        return settingMode;
    }

    public String getContentsCSS() {
        String cssHTML = "html{" +
                "font-family: \"" + settingMode.getFontFamily() + "\";\n" +
                "font-weight: " + settingMode.getFontWeight() + ";\n" +
                "font-style: " + settingMode.getFontStyle() + ";\n" +
                "padding: " + settingMode.getPaddingTopBottom() + "px " + settingMode.getPaddingLeftRight() + "px " +
                settingMode.getPaddingTopBottom() + "px " + settingMode.getPaddingLeftRight() + "px;\n" +
                "background-color: " + settingMode.getBgColor() + ";\n" +
                "color: " + settingMode.getTextColor() + ";\n" +
                "font-size:" + settingMode.getfontSize() + "%;\n" +
                "height: " + (settingMode.getHeight() - 2 * settingMode.getPaddingTopBottom()) + "px;\n" +
                "-webkit-column-gap: " + 2 * settingMode.getPaddingLeftRight() + "px;\n" +
                "-moz-column-gap: " + 2 * settingMode.getPaddingLeftRight() + "px;  column-gap: " + 2 * settingMode.getPaddingLeftRight() + "px;\n" +
                "-webkit-column-width: " + (settingMode.getWidth() - (2 * settingMode.getPaddingLeftRight())) + "px;\n" +
                "-moz-column-width: " + (settingMode.getWidth() - (2 * settingMode.getPaddingLeftRight())) + "px;\n" +
                "column-width:" + (settingMode.getWidth() - (2 * settingMode.getPaddingLeftRight())) + "px; \n" +
                "height:" + (settingMode.getHeight() - 2 * settingMode.getPaddingTopBottom()) + "px;\n" +
                "}\n";
        String cssID = "#blankpage{\n" +
                "width:" + settingMode.getWidth() + "px;\n" +
                "height:" + (settingMode.getHeight() - 2 * settingMode.getPaddingTopBottom()) + "px\n" +
                "}";
        contentsCSS = cssHTML + cssID;
        return contentsCSS;
    }

    public void setContentsCSS(String contentsCSS) {
        String cssHTML = "html{" +
                "font-family: \"" + settingMode.getFontFamily() + "\";\n" +
                "font-weight: " + settingMode.getFontWeight() + ";\n" +
                "font-style: " + settingMode.getFontStyle() + ";\n" +
                "padding: " + settingMode.getPaddingTopBottom() + "px " + settingMode.getPaddingLeftRight() + "px " +
                settingMode.getPaddingTopBottom() + "px " + settingMode.getPaddingLeftRight() + "px;\n" +
                "background-color: " + settingMode.getBgColor() + ";\n" +
                "color: " + settingMode.getTextColor() + ";\n" +
                "font-size:" + settingMode.getfontSize() + "%;\n" +
                "height: " + (settingMode.getHeight() - 2 * settingMode.getPaddingTopBottom()) + "px;\n" +
                "-webkit-column-gap: " + 2 * settingMode.getPaddingLeftRight() + "px;\n" +
                "-moz-column-gap: " + 2 * settingMode.getPaddingLeftRight() + "px;  column-gap: " + 2 * settingMode.getPaddingLeftRight() + "px;\n" +
                "-webkit-column-width: " + (settingMode.getWidth() - (2 * settingMode.getPaddingLeftRight())) + "px;\n" +
                "-moz-column-width: " + (settingMode.getWidth() - (2 * settingMode.getPaddingLeftRight())) + "px;\n" +
                "column-width:" + (settingMode.getWidth() - (2 * settingMode.getPaddingLeftRight())) + "px; \n" +
                "height:" + (settingMode.getHeight() - 2 * settingMode.getPaddingTopBottom()) + "px;\n" +
                "}\n";
        String cssID = "#blankpage{\n" +
                "width:" + settingMode.getWidth() + "px;\n" +
                "height:" + (settingMode.getHeight() - 2 * settingMode.getPaddingTopBottom()) + "px\n" +
                "}";
        contentsCSS = cssHTML + cssID;
        this.contentsCSS = contentsCSS;
    }

    public String getContentsJS() {
        String js = "document.getElementById(\"page\").innerHTML = loadPage('" + pathfileHtml + "');\n" +
                "function loadPage(href)\n" +
                "            {\n" +
                "                var xmlhttp = new XMLHttpRequest();\n" +
                "                xmlhttp.open(\"GET\", href, false);\n" +
                "                xmlhttp.send();\n" +
                "                return xmlhttp.responseText;\n" +
                "            }";
        this.contentsJS = js;
        return contentsJS;
    }

    public void setContentsJS(String contentsJS) {
        this.contentsJS = contentsJS;
    }

    public String getContentHTML() {
        return contentHTML;
    }

    public void setContentHTML(String contentHTML) {
        this.contentHTML = contentHTML;
    }

    public String getPathfileHtml() {
        return pathfileHtml;
    }

    public void setPathfileHtml(String pathfileHtml) {
        this.pathfileHtml = pathfileHtml;
    }
}
