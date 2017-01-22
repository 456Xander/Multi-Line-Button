package at.alex.multilinebutton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MultiLineButton extends JTextArea implements MouseListener {
	private Color defaultColor;
	private Color highlight, lightHighlight;
	private BtnState state;
	private List<ActionListener> actionListeners;

	public MultiLineButton(String text, Color defaultColor) {
		this.setEditable(false);
		this.setText(text);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.addMouseListener(this);
		this.setBorder(new EmptyBorder(5, 10, 5, 10));
		state = BtnState.NORMAL;
		this.defaultColor = defaultColor;
		this.setBackground(defaultColor);
		highlight = new Color(122, 138, 153);
		lightHighlight = new Color(184, 207, 229);
		// clickedColor = new Color(r, g, b);/
		actionListeners = new ArrayList<>();
	}

	@Override
	public Color getSelectionColor() {
		return getBackground();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setBackground(lightHighlight);
		state = BtnState.CLICKED;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (ActionListener l : actionListeners) {
			l.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, this.getText()));
		}
		setBackground(defaultColor);
		state = BtnState.NORMAL;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		state = BtnState.HOVERED;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setBackground(defaultColor);
		state = BtnState.NORMAL;
		repaint();
	}

	@Override
	public void paintBorder(Graphics g) {
		super.paintBorder(g);
		Graphics g2 = g.create();
		g2.setColor(highlight);
		switch (state) {
		case NORMAL:
			g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			break;
		case HOVERED:
			g2.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
			g2.setColor(lightHighlight);
			g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			g2.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
			break;
		case CLICKED:
			Border b = new BevelBorder(BevelBorder.LOWERED);
			b.paintBorder(this, g2, 0, 0, getWidth(), getHeight());
			break;
		}
		g2.dispose();
	}

	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}

	public List<ActionListener> getActionListeners() {
		return actionListeners;
	}
}
