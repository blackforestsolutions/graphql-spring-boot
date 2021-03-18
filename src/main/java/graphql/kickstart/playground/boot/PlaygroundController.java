package graphql.kickstart.playground.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class PlaygroundController {

    private static final String CDN_ROOT = "https://cdn.jsdelivr.net/npm/graphql-playground-react";
    private static final String CSS_PATH = "static/css/index.css";
    private static final String FAVICON_PATH =  "favicon.png";
    private static final String SCRIPT_PATH = "static/js/middleware.js";
    private static final String LOGO_PATH = "logo.png";

    private static final String CSS_URL_ATTRIBUTE_NAME = "cssUrl";
    private static final String FAVICON_URL_ATTRIBUTE_NAME = "faviconUrl";
    private static final String SCRIPT_URL_ATTRIBUTE_NAME = "scriptUrl";
    private static final String LOGO_URL_ATTRIBUTE_NAME = "logoUrl";

    private final PlaygroundPropertiesConfiguration propertiesConfiguration;

    private final ObjectMapper objectMapper;

    @GetMapping("${graphql.playground.mapping:/playground}")
    public String playground(final Model model, final HttpServletRequest request) {
        if (propertiesConfiguration.getPlayground().getCdn().isEnabled()) {
            setCdnUrls(model);
        } else {
            setLocalAssetUrls(model);
        }
        model.addAttribute("pageTitle", propertiesConfiguration.getPlayground().getPageTitle());
        model.addAttribute("properties", objectMapper.valueToTree(propertiesConfiguration.getPlayground()));
        model.addAttribute("_csrf", request.getAttribute("_csrf"));
        return "playground";
    }

    private String getCdnUrl(final String assetPath) {
        return String.format("%s@%s/build/%s", CDN_ROOT, propertiesConfiguration.getPlayground().getCdn().getVersion(),
                assetPath);
    }

    private String getLocalUrl(final String assetPath) {
        return Paths.get(propertiesConfiguration.getPlayground().getStaticPath().getBase(), assetPath).toString()
                .replace('\\', '/');
    }

    private void setCdnUrls(final Model model) {
        model.addAttribute(CSS_URL_ATTRIBUTE_NAME, getCdnUrl(CSS_PATH));
        model.addAttribute(FAVICON_URL_ATTRIBUTE_NAME, getCdnUrl(FAVICON_PATH));
        model.addAttribute(SCRIPT_URL_ATTRIBUTE_NAME, getCdnUrl(SCRIPT_PATH));
        model.addAttribute(LOGO_URL_ATTRIBUTE_NAME, getCdnUrl(LOGO_PATH));
    }

    private void setLocalAssetUrls(final Model model) {
        model.addAttribute(CSS_URL_ATTRIBUTE_NAME, getLocalUrl(CSS_PATH));
        model.addAttribute(FAVICON_URL_ATTRIBUTE_NAME, getLocalUrl(FAVICON_PATH));
        model.addAttribute(SCRIPT_URL_ATTRIBUTE_NAME, getLocalUrl(SCRIPT_PATH));
        model.addAttribute(LOGO_URL_ATTRIBUTE_NAME, getLocalUrl(LOGO_PATH));
    }
}
