package ch.supsi.fscli.frontend.model;

public interface IPreferencesModel {
    public String getLanguage();
    public void setLanguage(String lang);
    public int getOutputLines();
    public void setOutputLines(int lines);
    public void save();

}
