package theme;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ThemeSwitcherView implements Serializable {

    private static final long serialVersionUID = 1L;
	private List<String> themesNames;
    private int selectedThemeIndex = 0;

    public String getSelectedTheme() {
        return themesNames.get(selectedThemeIndex);
    }

    public void setSelectedIndex() {
        System.out.println("selectedThemeIndex: " + selectedThemeIndex);

        // Increment index and reset to 0 if it goes beyond the last theme
        selectedThemeIndex = (selectedThemeIndex + 1) % themesNames.size();
    }

    @PostConstruct
    public void init() {
        themesNames = List.of(
                "nova-light",
                "vela",
                "nova-dark",
                "nova-colored",
                "luna-blue",
                "luna-amber",
                "luna-green",
                "luna-pink"
                

        );
    }
}
