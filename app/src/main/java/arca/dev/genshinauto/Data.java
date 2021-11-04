package arca.dev.genshinauto;

public class Data {
    private final String message;
    private final String data;
    private final String retcode;

    public Data(String message, String data, String retcode){
        this.message = message;
        this.data = data;
        this.retcode = retcode;
    }

    public String getMessage(){
        return message;
    }

    public String getData(){
        return data;
    }

    public String getRetcode(){
        return retcode;
    }
}
