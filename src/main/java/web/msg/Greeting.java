package web.msg;

/**
 * Created by chriscu on 28/08/2014.
 */
public class Greeting {

    private String lang = "en";
    private String text;

    public Greeting() {}

    public Greeting(String lang, String text) {
        this.lang = lang;
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
