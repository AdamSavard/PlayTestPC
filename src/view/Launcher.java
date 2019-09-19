package view;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import mainframe.MainController;

public final class Launcher
{
    // The main entry Point for the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// Set look and feel to Nimbus
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
				    // If Nimbus is not available, fall back to cross-platform
				    try {
				        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				    } catch (Exception ex) {
				        // Fuck it
				    }
				}
				
				// Start the application
				try {
                    MainController control = new MainController();
					control.openView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}
		});
	}
}
