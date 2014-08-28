package web;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.http.client.utils.URIUtils;
import org.eclipse.jetty.util.URIUtil;
import web.msg.Greeting;
import web.msg.TranslateResponse;

import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Path("/greet") @Produces(MediaType.APPLICATION_JSON)
public class GreetService {

    private static final String YANDEX_KEY = "trnsl.1.1.20140828T134326Z.30710692cc793ec0.a6d94fdac879f18ba0757b5f66fdbefe4e0b6eb7";
    private static final String YANDEX_URL_TEMPLATE = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="+YANDEX_KEY+"&lang=en-%s&text=%s";
    private static final String GREETING_SERVICE_URL = "http://localhost:8180/greet";
    private static final List<String> WORKING_LANGS = Lists.newArrayList("en", "pl", "ro");
    private static final Client httpClient = Client.create();

    @GET @Timed
    public List<Greeting> greet(@QueryParam("hacker") final Optional<String> target) {

        return Lists.transform(WORKING_LANGS, new Function<String, Greeting>() {
            @Nullable @Override
            public Greeting apply(@Nullable String langCode) {
                WebResource greetingResource = httpClient.resource(GREETING_SERVICE_URL);
                if (target.isPresent())
                    greetingResource.queryParam("target", target.get());
                Greeting englishGreeting = greetingResource.get(Greeting.class);
                String englishText = urlEncode(englishGreeting.getText());

                TranslateResponse response = httpClient
                        .resource(String.format(YANDEX_URL_TEMPLATE, langCode, englishText))
                        .get(TranslateResponse.class);
                return new Greeting(langCode, response.getText().get(0));
            }
        });
    }

    private String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }
}
