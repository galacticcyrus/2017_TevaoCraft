package mygame;

/*
 * @author Ciro Peter Janos Lobo Mora RA 111310
 * @author Jose Estevao Oliveira RA 132184
 */
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.util.ArrayList;

import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapFont;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.LightNode;
import com.jme3.scene.shape.Sphere;

/**
 * Sample 8 - how to let the user pick (select) objects in the scene using the
 * mouse or key presses. Can be used for shooting, opening doors, etc.
 */
//Used Textures
//h ttp://maxpixel.freegreatpicture.com/Stone-Structure-Grey-Background-Material-Solid-255295
//h ttp://fabooguy.deviantart.com/art/Dirt-Ground-Texture-Tileable-2048x2048-441212191
//Used Music
//h ttps://www.youtube.com/watch?v=BDc0DlKaXEI
public class TesteTiro extends SimpleApplication {

    public static void main(String[] args) {

        new TesteTiro().start();

    }
    Node mundo;
    Geometry marca;
    int numbers = 0;
    ArrayList<Geometry> allCubes = new ArrayList<Geometry>();
    Texture monkeyTex;
    Texture monkeyTex2;
    boolean pause, audio, helpbool;
    private AudioNode music;
    private BitmapFont defaultFont;
    private BitmapText help, pressStart, standard, about;

    @Override
    public void simpleInitApp() {
        audio = true;
        pause = true;
        helpbool = false;
        mundo = new Node("lightParentNode");
        rootNode.attachChild(mundo);
        PointLight pl = new PointLight();
        pl.setColor(ColorRGBA.White);
        pl.setRadius(10f);
        PointLight pl2 = new PointLight();
        pl2.setColor(ColorRGBA.White);
        pl2.setRadius(10f);
        PointLight pl3 = new PointLight();
        pl3.setColor(ColorRGBA.White);
        pl3.setRadius(10f);
        PointLight pl4 = new PointLight();
        pl4.setColor(ColorRGBA.White);
        pl4.setRadius(10f);
        PointLight pl5 = new PointLight();
        pl5.setColor(ColorRGBA.White);
        pl5.setRadius(10f);
        PointLight pl6 = new PointLight();
        pl6.setColor(ColorRGBA.White);
        pl6.setRadius(10f);
        PointLight pl7 = new PointLight();
        pl7.setColor(ColorRGBA.White);
        pl7.setRadius(10f);

        rootNode.addLight(pl);
        rootNode.addLight(pl2);
        rootNode.addLight(pl3);
        rootNode.addLight(pl4);
        rootNode.addLight(pl5);
        rootNode.addLight(pl6);
        rootNode.addLight(pl7);

        LightNode lightNode1 = new LightNode("pointLight", pl);
        mundo.attachChild(lightNode1);

        LightNode lightNode2 = new LightNode("pointLight", pl2);
        lightNode2.setLocalTranslation(0f, 0f, 10f);
        mundo.attachChild(lightNode2);

        LightNode lightNode3 = new LightNode("pointLight", pl3);
        lightNode3.setLocalTranslation(10f, 0f, 0f);
        mundo.attachChild(lightNode3);

        LightNode lightNode4 = new LightNode("pointLight", pl4);
        lightNode4.setLocalTranslation(10f, 0f, 10f);
        mundo.attachChild(lightNode4);

        LightNode lightNode5 = new LightNode("pointLight", pl5);
        lightNode5.setLocalTranslation(0f, 0f, 20f);
        mundo.attachChild(lightNode5);

        LightNode lightNode6 = new LightNode("pointLight", pl6);
        lightNode6.setLocalTranslation(20f, 0f, 0f);
        mundo.attachChild(lightNode6);

        LightNode lightNode7 = new LightNode("pointLight", pl7);
        lightNode7.setLocalTranslation(20f, 0f, 20f);
        mundo.attachChild(lightNode7);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.Red);
        dl.setDirection(new Vector3f(0, 1, 0));
        rootNode.addLight(dl);

        /**
         * A white ambient light source.
         */
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        monkeyTex = assetManager.loadTexture("Textures/dirt.png");
        monkeyTex2 = assetManager.loadTexture("Textures/stone.jpg");
        initCrossHairs(); // a "+" in the middle of the screen to help aiming
        initKeys();       // load custom key mappings
        /**
         * create four colored boxes and a floor to shoot at:
         */

        music = new AudioNode(assetManager, "Sounds/Call_to_Adventure_Comedy.ogg", true);
        music.setPositional(false);
        music.setVolume(3);
        music.setLooping(true);
        music.play();

        setTerrain();

        defaultFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        pressStart = new BitmapText(defaultFont, false);
        help = new BitmapText(defaultFont, false);
        standard = new BitmapText(defaultFont, false);
        about = new BitmapText(defaultFont, false);

        loadText(pressStart, "PRESS P TO START AND BUILD ANYTHING \n(with stones)", defaultFont, 250, 5, 0);
        //loadText(standard, "PRESS H TO HELP", defaultFont, 500, 22, 0);
        loadText(about, "Github: https://github.com/galacticcyrus/2017_TevaoCraft \nJose Estevao Oliveira - 132184\nCiro Mora - 111310\nUsed Textures\n" +
"http://maxpixel.freegreatpicture.com/Stone-Structure-Grey-Background-Material-Solid-255295 \n" +
"http://fabooguy.deviantart.com/art/Dirt-Ground-Texture-Tileable-2048x2048-441212191 \n" +
"Used Music\n" +
"https://www.youtube.com/watch?v=BDc0DlKaXEI", defaultFont, 1, 22, 0);

    }

    private void initKeys() {
        inputManager.addMapping("Tiro", new KeyTrigger(KeyInput.KEY_SPACE), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "Tiro");
        inputManager.addMapping("Remove", new KeyTrigger(KeyInput.KEY_RETURN), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(actionListener, "Remove");
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionListener, "Pause");
        inputManager.addMapping("Restart", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(actionListener, "Restart");
        inputManager.addMapping("Help", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addListener(actionListener, "Help");
        inputManager.addMapping("Mute", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addListener(actionListener, "Mute");
    }

    private void setTerrain() {
        mundo = new Node("mesa");
        rootNode.attachChild(mundo);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mundo.attachChild(makeCube("Objeto 1" + j + i, 2 * j, -6f, 2 * i, "terra"));
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mundo.attachChild(makeCube("Objeto 2" + j + i, 2 * j, -4f, 2 * i, "pedra"));
            }
        }
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Tiro") && !keyPressed) {
                if (!pause) {
                    addCube();
                }
            }

            if (name.equals("Remove") && !keyPressed) {
                if (!pause) {
                    removeCube();
                }
            }

            if (name.equals("Pause") && !keyPressed) {
                Pause();
            }

            if (name.equals("Restart") && !keyPressed) {
                if (pause) {
                    loadText(pressStart, "PRESS P TO START AND BUILD ANYTHING \n(with stones)", defaultFont, 250, 5, 0);
                }
                rootNode.detachChild(mundo);
                setTerrain();

            }
            if (name.equals("Help") && !keyPressed) {
                if(!pause)
                    helpbool = !helpbool;
                if (helpbool) {
                    loadText(help, "Use WASD to fly around.\nUse Left Click to place Stone.\nUse Right Click to remove block.\nUse P to Pause\nUse M to mute audio. \nUse R to restart. \nUse Esc to quit.", defaultFont, 250, 20, 0);
                } else {
                    guiNode.detachChild(help);
                }
            }

            if (name.equals("Mute") && !keyPressed) {
                audio = !audio;
                if (audio) {
                    music.setVolume(3f);
                } else {
                    music.setVolume(0f);
                }
            }
        }

        protected CollisionResult getClosest() {
            // 1. Reinicia lista de resultados
            CollisionResults results = new CollisionResults();
            // 2. Redife o raio em fun鈬o da localica鈬o e dire鈬o da camera.
            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
            // 3. Verifica coliss e guarda em results
            mundo.collideWith(ray, results);
            // 4. Imprime coliss
            System.out.println("----- Coliss? " + results.size() + "-----");
            for (int i = 0; i < results.size(); i++) {
                float dist = results.getCollision(i).getDistance();
                Vector3f pt = results.getCollision(i).getContactPoint();
                String hit = results.getCollision(i).getGeometry().getName();
                System.out.println("O ray colidiu " + hit + " na posi鈬o " + pt
                        + ", " + dist + " de dist穗cia.");
            }
            if (results.size() > 0) { // 5. A軋o da colis縊
                // Qual ・a colis縊 mais prima?
                CollisionResult closest = results.getClosestCollision();
                return closest;
            }
            return null;
        }

        protected void addCube() {
            CollisionResult closest = getClosest();
            if (closest != null) {
                String nameClosest = closest.getGeometry().getName();
                System.out.println("O mais proximo ・ " + nameClosest);
                //Posiciona a marca.
                //allCubes.add(makeCube("CuboNovo " + numbers,0,0,0));
                //marca.setLocalTranslation(closest.getContactNormal());
                Spatial g = closest.getGeometry().getParent().clone();
                if (nameClosest.charAt(nameClosest.length() - 1) == '0') {
                    g.move(0, 0, 2);
                } else if (nameClosest.charAt(nameClosest.length() - 1) == '1') {
                    g.move(2, 0, 0);
                } else if (nameClosest.charAt(nameClosest.length() - 1) == '2') {
                    g.move(0, 0, -2);
                } else if (nameClosest.charAt(nameClosest.length() - 1) == '3') {
                    g.move(-2, 0, 0);
                } else if (nameClosest.charAt(nameClosest.length() - 1) == '4') {
                    g.move(0, -2, 0);
                } else if (nameClosest.charAt(nameClosest.length() - 1) == '5') {
                    g.move(0, 2, 0);
                }

                Material mat1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
                mat1.setTexture("DiffuseMap", monkeyTex2);
                g.setMaterial(mat1);

                rootNode.attachChild(g);  //allCubes.get(numbers));
                mundo.attachChild(g);
                numbers++;
            }
        }

        protected void removeCube() {
            CollisionResult closest = getClosest();
            if (closest != null) {

                String nameClosest = closest.getGeometry().getName();
                System.out.println("O mais proximo ・ " + nameClosest);
                rootNode.detachChild(closest.getGeometry().getParent());
                mundo.detachChild(closest.getGeometry().getParent());
                numbers++;
            }
        }
    };
    public static final Quaternion YAW090 = new Quaternion().fromAngleAxis(FastMath.PI / 2, new Vector3f(0, 1, 0));
    public static final Quaternion YAW180 = new Quaternion().fromAngleAxis(FastMath.PI, new Vector3f(0, 1, 0));
    public static final Quaternion YAW270 = new Quaternion().fromAngleAxis(FastMath.PI * 3 / 2, new Vector3f(0, 1, 0));
    public static final Quaternion PITCH090 = new Quaternion().fromAngleAxis(FastMath.PI / 2, new Vector3f(1, 0, 0));
    public static final Quaternion PITCH180 = new Quaternion().fromAngleAxis(FastMath.PI, new Vector3f(1, 0, 0));
    public static final Quaternion PITCH270 = new Quaternion().fromAngleAxis(FastMath.PI * 3 / 2, new Vector3f(1, 0, 0));

    /**
     * A cube object for target practice
     */

    protected Node makeCube(String name, float x, float y, float z, String tipo) {

        Box box = new Box(new Vector3f(0, 0, 1), 1, 1, 0);
        Geometry cube = new Geometry(name + 0, box);
        Geometry cube2 = new Geometry(name + 1, box);
        Geometry cube3 = new Geometry(name + 2, box);
        Geometry cube4 = new Geometry(name + 3, box);
        Geometry cube5 = new Geometry(name + 4, box);
        Geometry cube6 = new Geometry(name + 5, box);

        cube.setLocalRotation(Matrix3f.IDENTITY);
        cube2.setLocalRotation(YAW090);
        cube3.setLocalRotation(YAW180);
        cube4.setLocalRotation(YAW270);
        cube5.setLocalRotation(PITCH090);
        cube6.setLocalRotation(PITCH270);

        cube.setLocalTranslation(x, y, z);
        cube2.setLocalTranslation(x, y, z);
        cube3.setLocalTranslation(x, y, z);
        cube4.setLocalTranslation(x, y, z);
        cube5.setLocalTranslation(x, y, z);
        cube6.setLocalTranslation(x, y, z);

        Material mat1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        switch (tipo) {
            case "terra":
                mat1.setTexture("DiffuseMap", monkeyTex);
                break;
            case "pedra":
                mat1.setTexture("DiffuseMap", monkeyTex2);
                break;
        }
        cube.setMaterial(mat1);
        cube2.setMaterial(mat1);
        cube3.setMaterial(mat1);
        cube4.setMaterial(mat1);
        cube5.setMaterial(mat1);
        cube6.setMaterial(mat1);

        Node n = new Node();
        //attach todos os cubes.
        n.attachChild(cube);
        n.attachChild(cube2);
        n.attachChild(cube3);
        n.attachChild(cube4);
        n.attachChild(cube5);
        n.attachChild(cube6);
        //retorna o node
        return n;
    }

    /**
     * A cube object for target practice
     */
//  protected Geometry makeCube(String name, float x, float y, float z) {
//    Box box = new Box(new Vector3f(x, y, z), 1, 1, 1);
//    Geometry cube = new Geometry(name, box);
//    Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//    mat1.setColor("Color", ColorRGBA.randomColor());
//    cube.setMaterial(mat1);
//    return cube;
//  }
    protected void initCrossHairs() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    protected void Pause() {
        pause = !pause;
        if (pause) {
            loadText(pressStart, "PRESS P TO UNPAUSE OR R TO RESTART", defaultFont, 200, 5, 0);
            loadText(about, "Github: https://github.com/galacticcyrus/2017_TevaoCraft \nJose Estevao Oliveira - 132184\nCiro Mora - 111310\nUsed Textures\n" +
"http://maxpixel.freegreatpicture.com/Stone-Structure-Grey-Background-Material-Solid-255295 \n" +
"http://fabooguy.deviantart.com/art/Dirt-Ground-Texture-Tileable-2048x2048-441212191 \n" +
"Used Music\n" +
"https://www.youtube.com/watch?v=BDc0DlKaXEI", defaultFont, 1, 22, 0);
            guiNode.detachChild(standard);
        } else {63414214
            loadText(standard, "PRESS H TO HELP", defaultFont, 500, 22, 0);
            guiNode.detachChild(pressStart);
            guiNode.detachChild(about);
        }
    }

    private void loadText(BitmapText txt, String text, BitmapFont font, float x, float y, float z) {
        txt.setSize(font.getCharSet().getRenderedSize());
        txt.setLocalTranslation(/*txt.getLineWidth() **/x, txt.getLineHeight() * y, z);
        txt.setText(text);
        guiNode.attachChild(txt);
    }

}
