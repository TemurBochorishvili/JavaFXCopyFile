package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {


    @FXML
    TreeView<File> treeView;
    @FXML
    Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File file = new File("E:\\");
        TreeItem<File> root = fileTree(file);

        getMouseClickedFile();

        treeView.setRoot(root);

    }

    public TreeItem fileTree(File file) {

        TreeItem<File> innerFile1 = new TreeItem<>(file);

        if (file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                innerFile1.getChildren().add(fileTree(file1));
            }
        }

        return innerFile1;
    }

    public void getMouseClickedFile() {
        treeView.setCellFactory(tree -> {
            TreeCell<File> cell;
            cell = new TreeCell<File>() {
                @Override
                public void updateItem(File item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            };

            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    TreeItem<File> treeItem = cell.getTreeItem();
                    File file = new File(treeItem.getValue().toString());
                    if(file.isDirectory()){
                        System.out.println(file.getAbsoluteFile());

                        copyFileEnth(new File("C:\\Users\\Temo\\Desktop\\Test\\Temo.txt"),file);

                        System.exit(5);
                    }
                }
            });
            return cell;
        });
    }

    public boolean copyFileEnth(File from, File toParentDirectory){

        if (!(from.exists() && toParentDirectory.exists() &&
                from.isFile() && toParentDirectory.isDirectory())) {
            return false;
        }
        try(InputStream input = new FileInputStream(from);
            OutputStream output = new FileOutputStream(new File(toParentDirectory,from.getName()))
        ) {

            byte [] buffer = new byte[(int)from.length()];

            input.read(buffer);

            output.write(buffer);

            input.close();
            output.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
