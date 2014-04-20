package damhonglinh.view;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by damho_000 on 4/20/14.
 */
public class KulFileChooser extends JFileChooser {

    private String extension;

    public KulFileChooser(String extension) {
        this.extension = extension;
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setAcceptAllFileFilterUsed(false);
        setFileFilter(new KulFileChooser.CSVFileFilter());
    }

    @Override
    public void approveSelection() {
        File f = getSelectedFile();
        if (f.exists() && getDialogType() != OPEN_DIALOG) {
            int result = JOptionPane.showConfirmDialog(this,
                    "File already exists. Do you want to overwrite?",
                    "File exists", JOptionPane.YES_NO_CANCEL_OPTION);
            switch (result) {
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
                default:
                    return;
            }
        } else if (!f.exists() && getDialogType() == OPEN_DIALOG) {
            JOptionPane.showMessageDialog(this, "File Not Found!", "File Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        super.approveSelection();
    }

    @Override
    public File getSelectedFile() {
        if (super.getSelectedFile() == null) {
            return null;
        } else {
            return appendExtension(super.getSelectedFile(), extension);
        }
    }

    private class CSVFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String[] temp = f.getName().split("\\.");
            String ext = temp[temp.length - 1];
            if (ext.equals(extension))
                return true;
            else
                return false;
        }

        @Override
        public String getDescription() {
            return extension;
        }

    }

    private File appendExtension(File file, String fileExtension) {
        String[] temp = file.getName().split("\\.");
        String ext = temp[temp.length - 1];
        if (!ext.equalsIgnoreCase(fileExtension)) {
            file = new File(file.getPath() + "." + fileExtension);
        }
        return file;
    }
}
