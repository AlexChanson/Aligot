package utility;

import com.google.gson.Gson;
import graphics.Window;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Loader {
    private static String ressourcesPath = System.getProperty("user.dir") + File.separator + "ressources" + File.separator + "sprites" + File.separator;

    public static <T> ArrayList<T> loadAll(Class<T> type, String folder){
        System.out.println("LOADER: Loading all " + type.getName());
        System.out.println("--- --- Reporting CallStack --- ---");
        Arrays.asList(Thread.currentThread().getStackTrace()).forEach(System.out::println);
        System.out.println("--- --- CallStack Ends --- ---");
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
        JarFile jarFile = null;
        String pathToJar = "";
        String tempPath = System.getProperty("java.io.tmpdir");
        try {
            pathToJar = Loader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            System.out.println("Error: unable to locate jar context, this is a CRITICAL ERROR !");
            e.printStackTrace();
        }
        try {
            jarFile = new JarFile(pathToJar);
        } catch (IOException ignored) {}

        if(jarFile != null){
            String path = tempPath +  "aligot_ressources";
            Window.setRessourcesFolderPath(path + File.separator);
            ressourcesPath = path + File.separator;

            File repertoire = new File(path);
            repertoire.mkdir();
            if(repertoire.exists()){
                System.out.println("Info: Decompiling Assets in " + path);

                Enumeration<JarEntry> e = jarFile.entries();
                while (e.hasMoreElements()) {
                    JarEntry je = e.nextElement();
                    if(!je.isDirectory()  && je.getName().startsWith("sprites")){
                        String fileName = je.getName().replace("sprites/", "");
                        InputStream in = ClassLoader.getSystemResourceAsStream(je.getName());
                        File f = new File(path + File.separator + fileName);
                        try {
                            f.createNewFile();
                            FileOutputStream out = new FileOutputStream(f);
                            int read;
                            byte[] bytes = new byte[1024];

                            while ((read = in.read(bytes)) != -1) {
                                out.write(bytes, 0, read);
                            }
                            in.close();
                            System.out.println("Extracted : " + path + File.separator + fileName);
                        } catch (IOException e1) {
                            System.out.println("Unable to create File : " + path + File.separator + fileName);
                        }
                    }
                }

            }
            else {
                System.out.println("Error creating " + path + " directory check your permissions !");
            }
        }

    }

    public static String getSpriteFolderPath(){
        return ressourcesPath;
    }
}
