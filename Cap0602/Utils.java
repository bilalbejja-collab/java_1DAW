/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo6nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserPrincipal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franmatias
 */
public class Utils {

    /**
     * Leer un fichero de texto de una sola vez. Este método es interesante
     * cuando el fichero no tiene mucho tamaño y necesitamos leer muchos
     *
     * @param nombreFichero
     * @throws IOException
     */
    public static void leer(String nombreFichero) throws IOException {
        Path ruta = Paths.get(nombreFichero);
        try {
            byte[] bytes = Files.readAllBytes(ruta);

            for (byte elem : bytes) {
                System.out.print((char) elem);

            }
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println(" Fichero leído correctamente. ");
    }

    public static void leer2(String nombreFichero) throws IOException {
        Path ruta = Paths.get(nombreFichero);
        try {
            byte[] bytes = Files.readAllBytes(ruta);
            String datos = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(datos);
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println(" Fichero leído correctamente. ");
    }

    public static List<String> leer3(String nombreFichero) throws IOException {
        Path ruta = Paths.get(nombreFichero);
        //Charset charset = Charset.forName("ISO-8859-1");
        Charset charset = Charset.forName("UTF-8");
        List<String> datos = null;
        try {
            datos = Files.readAllLines(ruta, charset);

        } catch (IOException e) {
            System.err.println(e);
        } finally {
            System.out.println(" Fichero " + ruta.getFileName() + " leído correctamente.");
        }

        return datos;

    }

    /**
     * Escribir ficheros de texto
     *
     * @param nombreFichero
     * @param datos
     * @throws IOException
     */
    public static void escribir(String nombreFichero, String datos) throws IOException {
        Path ruta = Paths.get(nombreFichero);
        try {
            byte[] datosBytes = datos.getBytes("UTF-8");
            Files.write(ruta, datosBytes);
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println(" Fichero escrito correctamente. ");
    }

    /**
     * Escribe una lista
     *
     * @param nombreFichero
     * @param datos
     * @throws IOException
     */
    public static void escribir(String nombreFichero, List<String> datos) throws IOException {
        Path ruta = Paths.get(nombreFichero);
        Charset charset = Charset.forName("UTF-8");
        try {
            Files.write(ruta, datos, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println(" Fichero escrito correctamente. ");
    }

    /**
     *
     * @param nombreFichero
     * @return
     * @throws IOException
     */
    public static List<String> leerTexto(String nombreFichero) throws IOException {
        Path ruta = Paths.get(nombreFichero);

        Charset charset = Charset.forName("ISO-8859-1");
        try {
            return Files.readAllLines(ruta, charset);

        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Lectura usuando Channel y Buffer
     *
     * @param nombreFichero
     * @throws IOException
     */
    public static void leerPorCanal(String nombreFichero) throws IOException {
        RandomAccessFile fichero = new RandomAccessFile(nombreFichero, "rw");
        FileChannel fc = fichero.getChannel();

        long tama = fc.size();
        ByteBuffer buf = ByteBuffer.allocate((int) tama);

        int bytesLeer = fc.read(buf);
        while (bytesLeer != -1) {
            System.out.println("Leidos " + bytesLeer);
            buf.flip(); //Preparando el buffer para leer

            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            buf.clear(); //Cambio a modo escritura
            bytesLeer = fc.read(buf);
        }
        fichero.close();
    }

    /**
     * Escribir por canal
     *
     * @param nombreFichero
     * @param datos
     * @throws IOException
     */
    public static void escribirPorCanal(String nombreFichero, String datos) throws IOException {
        RandomAccessFile fichero = new RandomAccessFile(nombreFichero, "rw");
        FileChannel fc = fichero.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(128);
        buf.clear(); //Pasar a modo escritura
        buf.put(datos.getBytes());

        buf.flip();
        while (buf.hasRemaining()) {
            fc.write(buf);
        }

        fichero.close();
    }

    /**
     * Escribir a binario
     *
     * @param nombreFichero
     * @throws IOException
     */
    public static void escribirBinario(String nombreFichero) throws IOException {
        Path ruta = Paths.get(nombreFichero);
        int[] data = {100, 200, 300, 400};

        //Necesitamos reservar memoria
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        //Codificamos los datos de entero a bytes
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        //Se colocan los datos en el buffer
        intBuffer.put(data);

        try {
            byte[] datos = byteBuffer.array();
            Files.write(ruta, datos);
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("Fichero " + nombreFichero + " escrito en binario.");
    }

    public static void leerBinario(String nombreFichero) throws IOException {
        Path ruta = Paths.get(nombreFichero);

        int[] datos = new int[4];

        try {
            byte[] datoBinario = Files.readAllBytes(ruta);
            ByteBuffer bf = ByteBuffer.wrap(datoBinario);
            IntBuffer ib = bf.asIntBuffer();
            int i = 0;
            while (ib.hasRemaining()) {
                datos[i++] = ib.get();
            }

            System.out.println(Arrays.toString(datos));

        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("Fichero " + nombreFichero + " escrito en binario.");
    }

    public static void escribirBinario2(String nombreFichero) throws IOException {
        Path ruta = Paths.get(nombreFichero);
        String nombre = "Pepe Sanchez";
        int edad = 32;
        double altura = 1.83;
        double peso = 83;
        boolean casado = true;

        //Necesitamos reservar memoria
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.putInt(edad);
        byteBuffer.putDouble(altura);
        byteBuffer.putDouble(peso);
        byteBuffer.put(casado ? (byte) 1 : (byte) 0);
        byteBuffer.put(nombre.getBytes());

        try {
            byte[] datos = byteBuffer.array();
            Files.write(ruta, datos);
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("Fichero " + nombreFichero + " escrito en binario.");
    }

    public static void leerBinario2(String nombreFichero) throws IOException {
        Path ruta = Paths.get(nombreFichero);
        String nombre = "";
        int edad = 0;
        double altura = 0;
        double peso = 0;
        boolean casado;

        try {

            byte[] datoBinario = Files.readAllBytes(ruta);
            ByteBuffer bb = ByteBuffer.wrap(datoBinario);

            edad = bb.getInt();
            altura = bb.getDouble();
            peso = bb.getDouble();
            System.out.println(edad + " " + altura + " " + " " + peso);

        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("Fichero " + nombreFichero + " leido en binario.");
    }

    /**
     * Sistema de ficheros
     */
    public static void sistemaFicheros() {
        FileSystem sistemaFicheros = FileSystems.getDefault();
        System.out.println(sistemaFicheros.toString());
        System.out.println(sistemaFicheros.getSeparator());
        System.out.println(sistemaFicheros.supportedFileAttributeViews());
        System.out.println(sistemaFicheros.getRootDirectories());
        System.out.println(sistemaFicheros.provider());
        System.out.println(FileSystems.getFileSystem(Paths.get("/").toUri()));
    }

    /**
     * rutas
     */
    public static void rutas() {
        Path path = Paths.get("NetBeansProjects", "Capitulo6NIO", "ejemplo.dat");
        Path path2 = Paths.get("NetBeansProjects/Capitulo6NIO/ejemplo.dat");
        //Normalizando la ruta
        Path ruta = Paths.get("NetBeansProjects", "Capitulo6NIO", "ejemplo.dat").normalize();
        System.out.println(Paths.get("NetBeansProjects/Capitulo6NIO/ejemplo.dat"));
        System.out.println(Paths.get("NetBeansProjects/Capitulo6NIO/ejemplo.dat").normalize());
        //Ruta desde una URI
        Path ruta2 = Paths.get(URI.create("file:///netbeans/Capitulo6/ejemplo.txt"));
        Path ruta3 = FileSystems.getDefault().getPath("Capitulo6NIO", "ejemplo.dat").normalize();
        Path ruta4 = Paths.get(System.getProperty("user.home"), "Mis Documentos", "fichero.txt");
        System.out.println(path);
        System.out.println(ruta);
        System.out.println("Sistema de ficheros: " + ruta.getFileSystem());
        System.out.println("Raíz del fichero: " + ruta.getRoot());
        System.out.println("Saltos hasta el root comenzando desde 0: " + ruta.getNameCount());
        System.out.println("Padre del fichero: " + ruta.getParent());
        System.out.println("Ruta absoluta de un fichero: " + ruta.toAbsolutePath());
        for (int i = 0; i < ruta.getNameCount(); i++) {
            System.out.println("Nombre elemento " + i + " es: " + ruta.getName(i));
        }

        Path base = Paths.get("NetBeansProjects/Capitulo6NIO/ejemplo.dat");
        Path ruta_1 = base.resolve("ejemplo.txt");
        Path ruta_2 = base.resolve("ejemplo2.txt");
        System.out.println(ruta_1);
        System.out.println(ruta_2);
        Path ruta1_a_ruta2 = ruta_1.relativize(ruta_2);
        System.out.println(ruta1_a_ruta2);
        System.out.println(ruta_1.getParent().getParent().getFileName().relativize(ruta_2));

    }

    public static void metadatos() {
        FileSystem fs = FileSystems.getDefault();
        System.out.println(fs.supportedFileAttributeViews());

        for (FileStore disco : fs.getFileStores()) {
            boolean soportado = disco.supportsFileAttributeView(BasicFileAttributeView.class);
            System.out.println(disco.supportsFileAttributeView("basic"));
            System.out.println(disco.name() + " ---" + soportado);
        }

        for (FileStore disco : fs.getFileStores()) {
            try {
                long total = disco.getTotalSpace() / 1024;
                long used = (disco.getTotalSpace() - disco.getUnallocatedSpace()) / 1024;
                long avail = disco.getUsableSpace() / 1024;
                System.out.format("%-20s %12d %12d %12d%n", disco, total, used, avail);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Vista de atributos básicos
     */
    public static void vistaAtributosBasicos() {

        BasicFileAttributes attr = null;
        Path path = Paths.get("Capitulo6NIO", "ejemplo.dat");
        try {
            attr = Files.readAttributes(path.getFileName(), BasicFileAttributes.class);
        } catch (IOException e) {
            System.err.println(e);
        }
        if (attr != null) {
            System.out.println("Fichero-> tamaño: " + attr.size() + " bytes");
            System.out.println("Fichero-> fecha y hora de creación: " + attr.creationTime());
            System.out.println("Fichero-> último acceso: " + attr.lastAccessTime());
            System.out.println("Fichero-> última modificación: " + attr.lastModifiedTime());
            System.out.println("¿Es directorio? " + attr.isDirectory());
            System.out.println("¿Es un fichero regular? " + attr.isRegularFile());
            System.out.println("¿Es un enlace simbólico? " + attr.isSymbolicLink());
            System.out.println("¿Es otro? " + attr.isOther());
        } else {
            System.out.println("Sistema de vista no soportado");
        }
        //Obtener un solo atributo
        try {
            long size = (Long) Files.getAttribute(path.getFileName(),
                    "basic:size", NOFOLLOW_LINKS);
            System.out.println("Tamaño: " + size + " bytes");
        } catch (IOException e) {
            System.err.println(e);
        }

        //Modificar atributos de fichero        
        long time = System.currentTimeMillis();
        FileTime fileTime = FileTime.fromMillis(time);
        try {
            Files.getFileAttributeView(path.getFileName(),
                    BasicFileAttributeView.class).setTimes(fileTime, fileTime, fileTime);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Vista de atributos DOS
     */
    public static void vistaAtributosDos() {
        DosFileAttributes attr = null;
        Path path = Paths.get("Capitulo6NIO", "ejemplo.dat");
        try {
            attr = Files.readAttributes(path.getFileName(), DosFileAttributes.class);
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e);
        }
        if (attr != null) {
            System.out.println("¿Es de solo lectura? " + attr.isReadOnly());
            System.out.println("¿Está oculto? " + attr.isHidden());
            System.out.println("¿Es un archivo? " + attr.isArchive());
            System.out.println("¿Es de sistema? " + attr.isSystem());
        } else {
            System.out.println("Vista no soportada");
        }
    }

    /**
     * FileOwner Atributos de ficheros
     */
    public static void vistaAtributosFileOwner() throws IOException {
        UserPrincipal propietario;
        Path ruta = Paths.get("Capitulo6NIO", "ejemplo.dat");
        FileOwnerAttributeView foav = Files.getFileAttributeView(ruta,
                FileOwnerAttributeView.class);
        
        try {
            propietario = ruta.getFileSystem().getUserPrincipalLookupService().
                    lookupPrincipalByName("franmatias");
            System.out.println(propietario);
            propietario = (UserPrincipal) Files.getAttribute(ruta.getFileName(), "owner:owner", NOFOLLOW_LINKS);
            System.out.println(propietario);
            
            

            System.out.println(foav.name());
            //System.out.println(foav.getOwner());
            System.out.println();
            //GroupPrincipal grupo = Files.readAttributes(ruta, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS).group();
            //foav.setOwner(propietario);
        } catch (IOException e) {
            System.err.println(e);
        }
        if (foav == null) {
            System.out.println("Vista no soportada");
        }
    }

    /**
     *
     */
    public static void vistaAtributosUnix() {
        PosixFileAttributes attr = null;
        Path path = Paths.get("Capitulo6NIO", "ejemplo.dat");
        
        try {
            attr = Files.readAttributes(path.getFileName(), PosixFileAttributes.class);
            
            System.out.println("Propietario fichero: " + attr.owner().getName());
            System.out.println("Grupo fichero: " + attr.group().getName());
            System.out.println("Permisos de fichero: " + attr.permissions().toString());
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    /**
     * Vista atributos ACL
     */
    public static void vistaAtributosACL() {
        List<AclEntry> acllist = null;
        Path path = Paths.get("Capitulo6NIO", "ejemplo.dat");
        AclFileAttributeView aclview = Files.getFileAttributeView(path.getFileName(),
                AclFileAttributeView.class);
        try {
            acllist = aclview.getAcl();
            System.out.println(acllist.isEmpty());
            acllist = (List<AclEntry>) Files.getAttribute(path, "acl:acl", NOFOLLOW_LINKS);

            for (AclEntry aclentry : acllist) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("Principal: " + aclentry.principal().getName());
                System.out.println("Type: " + aclentry.type().toString());
                System.out.println("Permissions: " + aclentry.permissions().toString());
                System.out.println("Flags: " + aclentry.flags().toString());
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    /**
     *
     */
    public static void vistaAtributosNTFS() {
        FileSystem fs = FileSystems.getDefault();
        for (FileStore store : fs.getFileStores()) {
            try {
                long total_space = store.getTotalSpace() / 1024;
                long used_space = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
                long available_space = store.getUsableSpace() / 1024;
                boolean is_read_only = store.isReadOnly();
                System.out.println("--- " + store.name() + " --- " + store.type());
                System.out.println("Total space: " + total_space);
                System.out.println("Used space: " + used_space);
                System.out.println("Available space: " + available_space);
                System.out.println("Is read only? " + is_read_only);
            } catch (IOException e) {
                System.err.println(e);
            }
        }

    }

    /**
     *
     */
    public static void ficheros() {
        Path path = Paths.get("Capitulo6NIO", "ejemplo.dat");
        System.out.println(Files.isReadable(path.getFileName()));
        System.out.println(Files.isWritable(path.getFileName()));
        System.out.println(Files.isExecutable(path.getFileName()));
        System.out.println(Files.isRegularFile(path.getFileName(), LinkOption.NOFOLLOW_LINKS));

        System.out.println("\nVer contenido de una carpeta (sin filtros):");
        path = Paths.get("/Users/franmatias/NetBeansProjects/Capitulo6NIO/").normalize();

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            for (Path file : ds) {
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println("-----------------------------------------");
        System.out.println("\nVer contenido de una carpeta (con filtros):");

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, "*.{txt,dat,xml}")) {
            for (Path file : ds) {
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static int aleatorioRango(int min, int max) {
        Random aleatorio = new Random();
        return aleatorio.nextInt(max - min + 1) + min;
    }

    /**
     * Oposiciones
     */
    public static void sorteo() {

        try {
            List<String> datos = leer3("temario.txt");

            for (int i = 0; i < 5; i++) {
                int bolas = datos.size() - 1;
                System.out.println("Bola " + (i + 1) + ": " + datos.remove(aleatorioRango(0, bolas)));
            }
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Lista un directorio
     *
     * @param directorio
     */
    public static void listarDirectorio(String directorio) {
        Path ruta = Paths.get(directorio);

        try {
            if (Files.exists(ruta) && Files.isDirectory(ruta)) {
                Files.list(ruta)
                        .filter(Files::isRegularFile)
                        .filter(ruta1 -> ruta1.toString().endsWith(".txt"))
                        .forEach(System.out::println);
            }
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     */
    public static void crearDirRecursivo() {
        String[] carpetas = {"carpeta", "mis Doc", "docs", "excel", "imagenes"};

        try {
            Path rutaBase = Paths.get("ficheros");
            Path ruta;
            for (String carpeta : carpetas) {
                ruta = Paths.get(rutaBase.toString(), carpeta);
                Files.createDirectory(ruta);
            }
            //Path ruta = Paths.get("ficheros", "carpeta1", "carpeta2", "carpeta4");
            //Files.createDirectories(ruta);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws IOException
     */
    public static void recorridoRecursivo() throws IOException {
        Path rutaBase = Paths.get("ficheros");
        //List<Path> directorios = new LinkedList<>();
        Files.walkFileTree(rutaBase, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                //directorios.add(dir.toAbsolutePath());
                System.out.println(dir);
                return CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("PreF: " + file);
                return CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("Fallo: " + file);
                return CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("PosD: " + dir);
                return CONTINUE;
            }

        });
    }

    /**
     * Escribir en binario usando NewOutputStream
     *
     * @param nombreFichero
     */
    public static void escribirBinarioNIO(String nombreFichero) {
        Path ruta = Paths.get(nombreFichero);
        try (ObjectOutputStream salida = new ObjectOutputStream(
                new BufferedOutputStream(
                        Files.newOutputStream(ruta,
                                StandardOpenOption.WRITE,
                                StandardOpenOption.CREATE,
                                StandardOpenOption.TRUNCATE_EXISTING)
                ))) {
            salida.writeInt(12);
            salida.writeFloat(4);
            salida.writeUTF("Hola Mundo cruel");
            salida.writeDouble(2.232323);

        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param nombreFichero
     */
    public static void leerBinarioNIO(String nombreFichero) {
        Path ruta = Paths.get(nombreFichero);
        try (ObjectInputStream entrada = new ObjectInputStream(
                new BufferedInputStream(
                        Files.newInputStream(ruta,
                                StandardOpenOption.READ)
                ))) {

            System.out.println(entrada.readInt());
            System.out.println(entrada.readFloat());
            System.out.println(entrada.readUTF());
            System.out.println(entrada.readDouble());
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Pasar directamente de texto a binario
     */
    public static void tratarFichero() {
        String fichero = "datos_empresa.csv";
        List<String> datos = null;
        try {
            datos = leer3(fichero);

        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        Path ruta = Paths.get("empresas.dat");
        try (ObjectOutputStream salida = new ObjectOutputStream(
                new BufferedOutputStream(
                        Files.newOutputStream(ruta,
                                StandardOpenOption.WRITE,
                                StandardOpenOption.CREATE,
                                StandardOpenOption.TRUNCATE_EXISTING)
                ))) {

            //lectura
            Iterator<String> it = datos.iterator();
            String registro = it.next();
            
            while (it.hasNext()) {
                registro = it.next();
                String[] campos = registro.split(";");
                //nombre,latitud,longitud,telefono,email,web
                String nombre = campos[0];
                double latitud = Double.parseDouble(campos[1]);
                double longitud = Double.parseDouble(campos[2]);
                String telefono = campos[3];
                String email = campos[4];
                String web = campos[5];
                
                salida.writeUTF(nombre);
                salida.writeDouble(latitud);
                salida.writeDouble(longitud);
                salida.writeUTF(telefono);
                salida.writeUTF(email);
                salida.writeUTF(web);
                
            }
           
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
