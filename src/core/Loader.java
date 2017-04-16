package core;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Loader {
    public static <T> ArrayList<T> loadAll(Class<T> type, String folder){
        ArrayList<T> stuff = new ArrayList<>();
        String[] source = null;
        Gson gson = new Gson();
        try{
            source = loadFromDir(folder);
        } catch (IOException e){
            System.out.println("Switching to .jar mode");
            try {
                source = loadFromJar(folder);
            }catch (Exception e1){
                System.out.printf("Loading from jar failed !%n******************%n");
                e1.printStackTrace();
                System.out.println("******************");

            }
        }
        if(source == null)
            return stuff;
        for (String sourceFile :
                source) {
            try {
                stuff.add(gson.fromJson(sourceFile, type));
            } catch (Exception ignored){}
        }
        stuff.removeIf(Objects::isNull);
        return stuff;
    }

    private static String[] loadFromDir(String dirPath) throws IOException{
        String folder = System.getProperty("user.dir")+File.separator+"ressources"+File.separator+dirPath;
        File dir = new File(folder);
        String[] files = dir.list();
        if(files == null)
            throw new IOException();
        String[] result = new String[files.length];
        for (int i = 0; i < files.length; i++)
            result[i] = new String(Files.readAllBytes(Paths.get(folder + File.separator + files[i])));
        return result;
    }

    private static String[] loadFromJar(String dirPath) throws Exception {
        ArrayList<String> result = new ArrayList<>();
        JarFile jarFile = new JarFile(Loader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        Enumeration<JarEntry> e = jarFile.entries();

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(!je.isDirectory() && je.getName().startsWith(dirPath)){
                System.out.println(je.getName());
                InputStream in = ClassLoader.getSystemResourceAsStream(je.getName());
                System.out.println(in.available());
                 result.add(new BufferedReader(new InputStreamReader(in))
                            .lines().collect(Collectors.joining("\n")));
                    in.close();
            }
        }

        return result.toArray(new String[result.size()]);
    }

    public static void decompileAssets(){
        //TODO Decompile Assets in the jar directory if not present
    }

    public static String getSpriteFolderPath(){
        //TODO do this dynamically
        return System.getProperty("user.dir") + File.separator + "ressources" + File.separator + "sprites" + File.separator;
    }
}
