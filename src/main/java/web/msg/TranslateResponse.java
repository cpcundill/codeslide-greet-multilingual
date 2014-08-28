package web.msg;

import java.util.List;

/**
 * Created by chriscu on 28/08/2014.
 */
public class TranslateResponse {

    private String code;
    private String lang;
    private List<String> text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}
