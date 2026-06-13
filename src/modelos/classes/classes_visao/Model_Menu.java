package modelos.classes.classes_visao;

import javax.swing.ImageIcon;

public class Model_Menu {
    public enum MenuType {
        MENU, TITLE, HOME, VEICULOS, DESPESAS, MOVIMENTACOES
    }

    private String iconName;
    private String name;
    private MenuType type;

    public Model_Menu(String iconName, String name, MenuType type) {
        this.iconName = iconName;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public MenuType getType() {
        return type;
    }

    public ImageIcon selecionarIconeCinza() {
        return new ImageIcon(getClass().getResource(
                "/imagens/" + iconName + "_cinza.png"));
    }

    public ImageIcon selecionarIconeLaranja() {
        return new ImageIcon(getClass().getResource(
                "/imagens/" + iconName + "_laranja.png"));
    }

    public ImageIcon toIcon() {
        String resourcePath = "/imagens/" + iconName;
        java.net.URL url = getClass().getResource(resourcePath);
        if (url != null) {
            return new ImageIcon(url);
        }

        java.io.File f = new java.io.File("src/imagens/" + iconName);
        if (f.exists()) {
            return new ImageIcon(f.getAbsolutePath());
        }

        System.err.println("Imagem não encontrada: " + resourcePath);
        return new ImageIcon();
    }
}
