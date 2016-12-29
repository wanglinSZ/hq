package com.hq.sina.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VersionUtil {
	private Point oldP;
	private TipWindow tw = null;
	private ImageIcon img = null;
	private JLabel imgLabel = null;
	private JPanel headPan = null;
	private JPanel feaPan = null;
	private JPanel btnPan = null;
	private JLabel title = null;
	private JLabel head = null;
	private JLabel close = null;
	private JTextArea feature = null;
	private JScrollPane jfeaPan = null;
	private JLabel sure = null;

	public VersionUtil(String message) {
		init(message);
		handle();
		this.tw.setAlwaysOnTop(true);
		this.tw.setUndecorated(true);
		this.tw.setResizable(false);
		this.tw.setVisible(true);
		this.tw.run();
	}

	public void init(String feaMap) {
		this.tw = new TipWindow(300, 220);

		this.img = new ImageIcon("src/background.gif");
		this.imgLabel = new JLabel(this.img);

		this.headPan = new JPanel(new FlowLayout(1, 0, 0));
		this.feaPan = new JPanel(new FlowLayout(1, 0, 0));
		this.btnPan = new JPanel(new FlowLayout(1, 0, 0));

		this.title = new JLabel("回购提示" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		this.close = new JLabel(" x");
		this.feature = new JTextArea(feaMap);
		this.jfeaPan = new JScrollPane(this.feature);

		this.sure = new JLabel("确定");
		this.sure.setHorizontalAlignment(0);

		((JPanel) this.tw.getContentPane()).setOpaque(true);
		this.headPan.setOpaque(true);
		this.feaPan.setOpaque(true);
		this.btnPan.setOpaque(true);

		this.tw.getLayeredPane().add(this.imgLabel, new Integer(Integer.MIN_VALUE));
		this.imgLabel.setBounds(0, 0, this.img.getIconWidth(), this.img.getIconHeight());
		this.headPan.setPreferredSize(new Dimension(300, 30));

		this.tw.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

		this.title.setPreferredSize(new Dimension(260, 26));
		this.title.setVerticalTextPosition(0);
		this.title.setHorizontalTextPosition(0);
		this.title.setFont(new Font("宋体", 0, 12));
		this.title.setForeground(Color.black);

		this.close.setFont(new Font("Arial", 1, 15));
		this.close.setPreferredSize(new Dimension(20, 20));
		this.close.setVerticalTextPosition(0);
		this.close.setHorizontalTextPosition(0);
		this.close.setCursor(new Cursor(12));
		this.close.setToolTipText("关闭");

		this.feature.setEditable(false);
		this.feature.setForeground(Color.red);
		this.feature.setFont(new Font("宋体", 0, 13));
		this.feature.setBackground(new Color(184, 230, 172));

		this.feature.setLineWrap(true);

		this.jfeaPan.setPreferredSize(new Dimension(250, 200));

		this.jfeaPan.setBackground(Color.black);

		this.sure.setPreferredSize(new Dimension(110, 30));

		this.sure.setCursor(new Cursor(12));

		this.headPan.add(this.title);
		this.headPan.add(this.close);

		this.feaPan.add(this.jfeaPan);

		this.btnPan.add(this.sure);

		this.tw.add(this.headPan, "North");
		this.tw.add(this.feaPan, "Center");
		this.tw.add(this.btnPan, "South");
	}

	public void handle() {
		this.sure.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(VersionUtil.this.tw, "谢谢，再见");
				VersionUtil.this.tw.close();
			}

			public void mouseEntered(MouseEvent e) {
				VersionUtil.this.sure.setBorder(BorderFactory.createLineBorder(Color.gray));
			}

			public void mouseExited(MouseEvent e) {
				VersionUtil.this.sure.setBorder(null);
			}
		});
		this.title.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point newP = new Point(e.getXOnScreen(), e.getYOnScreen());
				int x = VersionUtil.this.tw.getX() + (newP.x - VersionUtil.this.oldP.x);
				int y = VersionUtil.this.tw.getY() + (newP.y - VersionUtil.this.oldP.y);
				VersionUtil.this.tw.setLocation(x, y);
				VersionUtil.this.oldP = newP;
			}
		});
		this.title.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				VersionUtil.this.oldP = new Point(e.getXOnScreen(), e.getYOnScreen());
			}
		});
		this.close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				VersionUtil.this.tw.close();
			}

			public void mouseEntered(MouseEvent e) {
				VersionUtil.this.close.setBorder(BorderFactory.createLineBorder(Color.gray));
			}

			public void mouseExited(MouseEvent e) {
				VersionUtil.this.close.setBorder(null);
			}
		});
	}
}