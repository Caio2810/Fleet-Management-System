package visao_widgets;

import themes.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReportItem extends JPanel {

    private JLabel icon;
    private JLabel title;
    private JLabel arrow;
    private boolean hover = false;

    public ReportItem(String text, Icon leftIcon) {
        setLayout(new BorderLayout(16, 0));

        icon = new JLabel(leftIcon);
        title = new JLabel(text);
        arrow = new JLabel(">");

        icon.setForeground(Theme.SUBTITLE);

        title.setForeground(Theme.ACCENT2);
        title.setFont(new java.awt.Font(".DIN Alternate", 0, 14));

        arrow.setForeground(Theme.SUBTITLE2);
        arrow.setFont(new java.awt.Font(".DIN Alternate", 0, 12));

        add(icon, BorderLayout.WEST);
        add(title, BorderLayout.CENTER);
        add(arrow, BorderLayout.EAST);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (hover) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(40, 40, 40));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);

            g2.dispose();
        }

        super.paintComponent(g);
    }
}