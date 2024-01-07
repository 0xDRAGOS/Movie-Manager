package gui;

import entity.Movie;
import utility.ReportCreator;
import repository.MovieRepository;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * provides UI panel for add, modify, delete, create report buttons and menu
 *
 * @author Simion Dragos Ionut
 */
public class PageStartPanel extends JPanel {
    private final MovieRepository movieRepository;
    private final ReportCreator reportCreator;

    public PageStartPanel(){
        setLayout(new FlowLayout(FlowLayout.LEFT));

        movieRepository = new MovieRepository();
        reportCreator = new ReportCreator();
        JButton addMovieButton = new JButton("Add");
        JButton modifyMovieButton = new JButton("Modify");
        JButton deleteMovieButton = new JButton("Delete");
        JButton createReportButton = new JButton("Create Report");

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem addMenuItem = new JMenuItem("Add");
        JMenuItem modifyMenuItem = new JMenuItem("Modify");
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        JMenuItem createReportMenuItem = new JMenuItem("Create Report");

        menu.add(addMenuItem);
        menu.add(modifyMenuItem);
        menu.add(deleteMenuItem);
        menu.add(createReportMenuItem);
        menuBar.add(menu);
        add(menuBar);
        add(addMovieButton);
        add(modifyMovieButton);
        add(deleteMovieButton);
        add(createReportButton);

        // menu items
        addMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFrame();
            }
        });

        modifyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        modifyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModifyFrame();
            }
        });

        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteFrame();
            }
        });

        createReportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        createReportMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Movie> movieList = movieRepository.getMovies();
                reportCreator.addAll(movieList);

                if(!reportCreator.createReport("Main/reports/movies_report.txt")){
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Could not create movie report.", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Movie report created successfully.", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // buttons
        addMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFrame();
            }
        });

        deleteMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteFrame();
            }
        });

        modifyMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModifyFrame();
            }
        });

        createReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Movie> movieList = movieRepository.getMovies();
                reportCreator.addAll(movieList);

                if(!reportCreator.createReport("Main/reports/movies_report.txt")){
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Could not create movie report.", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Movie report created successfully.", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
