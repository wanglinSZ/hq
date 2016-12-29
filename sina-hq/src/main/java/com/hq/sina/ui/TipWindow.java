package com.hq.sina.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JDialog;

class TipWindow extends JDialog {
	private static Dimension dim;
	private int x;
	private int y;
	private int width;
	private int height;
	private static Insets screenInsets;

	public TipWindow(int width, int height) {
		this.width = width;
		this.height = height;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		this.x = ((int) (dim.getWidth() - width - 3.0D));
		this.y = ((int) (dim.getHeight() - screenInsets.bottom - 3.0D));
		initComponents();
	}

	public void run() {
		for (int i = 0; i <= this.height; i += 10) {
			try {
				setLocation(this.x, this.y - i);
				Thread.sleep(5L);
			} catch (InterruptedException localInterruptedException1) {
			}
		}
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		close();
	}

	private void initComponents() {
		setSize(this.width, this.height);
		setLocation(this.x, this.y);
		setBackground(Color.black);
	}

	public void close() {
		this.x = getX();
		this.y = getY();
		int ybottom = (int) dim.getHeight() - screenInsets.bottom;
		for (int i = 0; i <= ybottom - this.y; i += 10) {
			try {
				setLocation(this.x, this.y + i);
				Thread.sleep(5L);
			} catch (InterruptedException localInterruptedException) {
			}
		}
		dispose();
	}
}
