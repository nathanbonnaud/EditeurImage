package com.example.nathan.editeurprojet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.rssample.*;


public class EditeurImg extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    boolean on = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeur_img);

        // Recupère et affiche la taille de l'image//


        ImageView i = (ImageView) findViewById(R.id.imageView5);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), R.mipmap.index, options);
        i.setImageBitmap(image1);

        ImageView i2 = (ImageView) findViewById(R.id.imageView);
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.mipmap.index2, options);
        i2.setImageBitmap(image2);
        //ContrasteCouleursDRS(image1);
        Floulissage(image1,image2);
        /*
        int largeur = options.outWidth;  /// * 2.6
        int hauteur = options.outHeight;   /// *2.6
        System.out.println("largeur:" + largeur + " hauteur : " + hauteur); */
        TextView tv = (TextView) findViewById(R.id.taille);
        tv.setText("Mon Editeur d'Image");








        /// MENU DEROULANT///


        Spinner monspinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> monadaptater = new ArrayAdapter<String>(EditeurImg.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Monspinner));
        monadaptater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monspinner.setAdapter(monadaptater);
        monspinner.setOnItemSelectedListener(this);

        /////

    }

    // CONFIGURATION MENU ///



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        ImageView i = (ImageView) findViewById(R.id.imageView5);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), R.mipmap.index, options);

        ImageView i2 = (ImageView) findViewById(R.id.imageView);
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inMutable = true;
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.mipmap.index2, options);


        switch (position) {
            case 0:
                break;
            case 1:
                ToGrey(image1);
                on = true;
                break;
            case 2:
                toGreyRS(image1);
                on = true;
                break;
            case 3:
                Coloriser(image1);
                on = true;
                break;
            case 4:
                ColoriserRS(image1);
                on =true;
                break;
            case 5:
                Conserve(image1,"red");
                on = true;
                break;
            case 6:
                ConserveRS(image1);
                on = true;
                break;
            case 7:
                ContrasteCouleur(image2,true );
                on = true;
                break;
            case 8:
                ContrasteCouleur(image2,false );
                on = true;
                break;
        }
        i.setImageBitmap(image1);
        i2.setImageBitmap(image2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    /// RENDRE UNE IMAGE GRISE///


    ///JAVA VERSION///


    protected void ToGrey(Bitmap bmp) {
        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++) {
                int a = bmp.getPixel(x,y);
                double Gris = 0.3 * Color.red(a) + 0.59 * Color.green(a) + 0.11 * Color.blue(a);
                bmp.setPixel(x, y, Color.argb(1, (int) Gris, (int) Gris, (int) Gris));
            }
        }
    }


    ///RENDERSCRIPT VERSION///


    private void toGreyRS(Bitmap bmp) {
        RenderScript rs = RenderScript.create(this);

        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());

        ScriptC_grey Grey = new ScriptC_grey(rs);

        Grey.forEach_toGrey(input, output);
        output.copyTo(bmp);

        input.destroy();
        output.destroy();
        Grey.destroy();
        rs.destroy();

    }



    /// CHANGER LA TEINTE D'UNE IMAGE ///


    ///JAVA VERSION///


    protected void Coloriser(Bitmap bmp) {
        float aleatoire = (float) (Math.random() * 360);
        float HSV[] = {0, 0, 0};
        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++) {
                int a = bmp.getPixel(x, y);

                Color.RGBToHSV(Color.green(a), Color.red(a), Color.blue(a), HSV);
                HSV[0] = aleatoire;
                bmp.setPixel(x, y, Color.HSVToColor(HSV));
            }
        }
    }


    /// RENDERSCRIPT VERSION///


    private void ColoriserRS(Bitmap bmp) {
        RenderScript rs = RenderScript.create(this);

        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());

        ScriptC_teinte ColorScript = new ScriptC_teinte(rs);
        int rand1 =(int) (Math.random() * 360) ;

        ColorScript.set_rand1(rand1);

        ColorScript.forEach_toColor(input, output);
        output.copyTo(bmp);

        input.destroy();
        output.destroy();
        ColorScript.destroy();
        rs.destroy();

    }




    /// CONSERVER UNE COULEUR ///



    /// JAVA VERSION ///

    private void Conserve(Bitmap bmp, String color) {
        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++) {
                int a = bmp.getPixel(x, y);
                if ( color == "red"){
                    double Gris = 0.3 * Color.red(a) + 0.59 * Color.green(a) + 0.11 * Color.blue(a);
                    if (Color.green(a)<= 100 && Color.blue(a) <= 100 && Color.red(a)> Color.green(a) && Color.red(a)>Color.blue(a)){
                        bmp.setPixel(x, y, Color.argb(1,Color.red(a), Color.green(a) ,Color.blue(a) ));
                    }else{
                        bmp.setPixel(x, y, Color.argb(1,(int) Gris, (int) Gris, (int) Gris));
                    }

                }else if( color == "green"){
                    double Gris = 0.3 * Color.red(a) + 0.59 * Color.green(a) + 0.11 * Color.blue(a);
                    if (Color.blue(a)<= 200 && Color.red(a) <= 200 && Color.green(a)> Color.red(a) && Color.green(a)>Color.blue(a)){
                        bmp.setPixel(x, y, Color.argb(1,Color.red(a), Color.green(a) ,Color.blue(a) ));
                    }else{
                        bmp.setPixel(x, y, Color.argb(1,(int) Gris, (int) Gris, (int) Gris));
                    }

                }else if( color == "blue"){
                    double Gris = 0.3 * Color.red(a) + 0.59 * Color.green(a) + 0.11 * Color.blue(a);
                    if (Color.green(a)<= 200 && Color.red(a) <= 200 && Color.blue(a)> Color.red(a) && Color.blue(a)>Color.green(a)){
                        bmp.setPixel(x, y, Color.argb(1,Color.red(a), Color.green(a) ,Color.blue(a) ));
                    }else{
                        bmp.setPixel(x, y, Color.argb(1,(int) Gris, (int) Gris, (int) Gris));
                    }

                }

            }

        }

    }

    /// RENDERSCRIPT VERSION ///


    private void ConserveRS(Bitmap bmp) {
        RenderScript rs = RenderScript.create(this);

        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());

        ScriptC_conserve Conserve = new ScriptC_conserve(rs);

        Conserve.forEach_toConserve(input, output);
        output.copyTo(bmp);

        input.destroy();
        output.destroy();
        Conserve.destroy();
        rs.destroy();

    }


    /// CONTRASTE GRIS ///


    private int[] GreyLevel(int[] pixels , int height , int width){
        int [] Greylevel = new  int[width*height];
        for (int i =0; i< pixels.length ; i++){
            Greylevel[i] = (int) (0.3 * Color.red(pixels[i]) + 0.59 * Color.green(pixels[i]) + 0.11 * Color.blue(pixels[i]));
        }

        return Greylevel;
    }



    private void ContrasteDynamiquePlus(Bitmap bmp){


        // initialise un tableau avec les pixels de l'image //

        int [] pixel = new  int[bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixel,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());


        // calcul le niveau de gris de chaque pixel et le place dans un tableau//
        int[] greytab = GreyLevel(pixel,bmp.getHeight(),bmp.getWidth());


        int[] hist = new int[256];
        for (int index = 0; index < greytab.length; index++) {

            // créer l'histogramme à partir du tableau des niveaux de gris //
            hist[greytab[index]]++;

        }


        //recupère le max et min de l'histogramme //

        int max = 0;
        int min = 0;
        int compteur = 0;

        while (hist[compteur] == 0){
            compteur++;
        }
        min = compteur;
        compteur = 255;

        while (hist[compteur] == 0) {
            compteur --;
        }
        max = compteur;


        // contraste dynamique //

        int [] newpixel = new int[bmp.getWidth()*bmp.getHeight()];
        for (int x = 0; x < pixel.length; x++){

            int greypixel = (255*(greytab[x] - min))/(max - min);

            newpixel[x] = Color.argb(1,greypixel,greypixel,greypixel);
        }
        bmp.setPixels(newpixel,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());
    }

    private void ContrasteDynamiqueMoins(Bitmap bmp) {


        // initialise un tableau avec les pixels de l'image //

        int[] pixel = new int[bmp.getHeight() * bmp.getWidth()];
        bmp.getPixels(pixel, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());


        // calcul le niveau de gris de chaque pixel et le place dans un tableau//
        int[] greytab = GreyLevel(pixel, bmp.getHeight(), bmp.getWidth());


        // ressert l'histogramme  //


        for (int x =0; x < greytab.length; x++){
            if (greytab[x] < 127){
                greytab[x] = greytab[x] + 20;
            } else if (greytab[x] > 127) {
                greytab[x] = greytab[x] - 20;
            }

        }


        //
        int[] hist = new int[256];
        for (int index = 0; index < greytab.length; index++) {

            // créer l'histogramme à partir du tableau des niveaux de gris //
            hist[greytab[index]]++;

        }


        //recupère le max et min de l'histogramme //

        int max = 0;
        int min = 0;
        int compteur = 0;

        while (hist[compteur] == 0) {
            compteur++;
        }
        min = compteur;
        compteur = 255;

        while (hist[compteur] == 0) {
            compteur--;
        }
        max = compteur;


        // diminution histogramme //

        int[] newpixel = new int[bmp.getWidth() * bmp.getHeight()];
        for (int x = 0; x < pixel.length; x++) {

            int greypixel = greytab[x];

            newpixel[x] = Color.argb(1, greypixel, greypixel, greypixel);
        }
        bmp.setPixels(newpixel, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

    }


    /// CONTRASTE COULEURS ///

    private int[] Couleurlevel(int[] pixels , int height , int width ,char c) {
        int[] Couleurlevel = new int[width * height];
        if (c == 'r'){
            for (int i = 0; i < pixels.length; i++) {
                Couleurlevel[i] = Color.red(pixels[i]);
            }
        }else if(c == 'g'){
            for (int i =0; i < pixels.length; i++){
                Couleurlevel[i] = Color.green(pixels[i]);
            }
        }else if(c == 'b'){
            for (int i =0; i< pixels.length; i++){
                Couleurlevel[i] = Color.blue(pixels[i]);
            }
        }


        return Couleurlevel;

    }

    /// La variable Changement indique on diminue ( False) ou augmente (True) le Contraste ///


    private void ContrasteCouleur(Bitmap bmp, boolean Changement) {


        // initialise un tableau avec les pixels de l'image //

        int[] pixel = new int[bmp.getHeight() * bmp.getWidth()];
        bmp.getPixels(pixel, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());


        // calcul le niveau de de rouge, vert et bleue de chaque pixel et le place dans  3 tableaux différents//

        int[] redtab = Couleurlevel(pixel, bmp.getHeight(), bmp.getWidth(), 'r');
        int[] greentab = Couleurlevel(pixel, bmp.getHeight(), bmp.getWidth(), 'g');
        int[] bluetab = Couleurlevel(pixel, bmp.getHeight(), bmp.getWidth(), 'b');


        if (Changement == false){


            // ressert l'histogramme ( diminituion du constraste)  //


        for (int x =0; x < redtab.length; x++){
            if (redtab[x] < 127){
                redtab[x] = redtab[x] + 15;
            } else if (redtab[x] > 127) {
                redtab[x] = redtab[x] - 15;
            }

        }

        for (int x =0; x < redtab.length; x++){
            if (greentab[x] < 127){
                greentab[x] = greentab[x] + 15;
            } else if (greentab[x] > 127) {
                greentab[x] = greentab[x] - 15;
            }

            }

            for (int x =0; x < redtab.length; x++){
                if (bluetab[x] < 127){
                    bluetab[x] = bluetab[x] + 15;
                } else if (bluetab[x] > 127) {
                    bluetab[x] = bluetab[x] - 15;
                }

            }

        int[] histred = new int[256];
        int[] histgreen = new int[256];
        int[] histblue = new int[256];

            /// Créer 3 histogrammes pour chaque couleurs ///


            for (int index = 0; index < redtab.length; index++) {
                histred[redtab[index]]++;
            }

            for (int index = 0; index < greentab.length; index++) {
                histgreen[greentab[index]]++;
            }

            for (int index = 0; index < bluetab.length; index++) {
                histblue[bluetab[index]]++;
            }


            //recupère le max et min de chaque histogrammes  //

            int maxred = 0;
            int minred = 0;
            int compteur = 0;

            while (histred[compteur] == 0) {
                compteur++;
            }
            minred = compteur;
            compteur = 255;

            while (histred
                    [compteur] == 0) {
                compteur--;
            }
            maxred = compteur;

            //

            int maxgreen = 0;
            int mingreen = 0;
            compteur = 0;

            while (histgreen[compteur] == 0) {
                compteur++;
            }
            mingreen = compteur;
            compteur = 255;

            while (histgreen[compteur] == 0) {
                compteur--;
            }
            maxgreen = compteur;


            int maxblue = 0;
            int minblue = 0;
            compteur = 0;

            while (histblue[compteur] == 0) {
                compteur++;
            }
            minblue = compteur;
            compteur = 255;

            while (histblue[compteur] == 0) {
                compteur--;
            }
            maxblue = compteur;


            int[] newpixel = new int[bmp.getWidth() * bmp.getHeight()];

            for (int x = 0; x < pixel.length; x++) {

                int redpixel = redtab[x];
                int greenpixel = greentab[x];
                int bluepixel = bluetab[x] ;

                newpixel[x] = Color.argb(1, redpixel, greenpixel, bluepixel);
            }
            bmp.setPixels(newpixel, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());


            //////////



        } else if (Changement == true) {


            int[] histred = new int[256];
            int[] histgreen = new int[256];
            int[] histblue = new int[256];

            /// Créer 3 histogrammes pour chaque couleurs ///


            for (int index = 0; index < redtab.length; index++) {
                histred[redtab[index]]++;
            }

            for (int index = 0; index < greentab.length; index++) {
                histgreen[greentab[index]]++;
            }

            for (int index = 0; index < bluetab.length; index++) {
                histblue[bluetab[index]]++;
            }


            //recupère le max et min de chaque histogrammes  //

            int maxred = 0;
            int minred = 0;
            int compteur = 0;

            while (histred[compteur] == 0) {
                compteur++;
            }
            minred = compteur;
            compteur = 255;

            while (histred
                    [compteur] == 0) {
                compteur--;
            }
            maxred = compteur;

            //

            int maxgreen = 0;
            int mingreen = 0;
            compteur = 0;

            while (histgreen[compteur] == 0) {
                compteur++;
            }
            mingreen = compteur;
            compteur = 255;

            while (histgreen[compteur] == 0) {
                compteur--;
            }
            maxgreen = compteur;


            int maxblue = 0;
            int minblue = 0;
            compteur = 0;

            while (histblue[compteur] == 0) {
                compteur++;
            }
            minblue = compteur;
            compteur = 255;

            while (histblue[compteur] == 0) {
                compteur--;
            }
            maxblue = compteur;


            int[] newpixel = new int[bmp.getWidth() * bmp.getHeight()];

            for (int x = 0; x < pixel.length; x++) {

                int redpixel = 255*(redtab[x] - minred)/(maxred - minred);
                int greenpixel = 255*(greentab[x] - mingreen)/(maxgreen - mingreen);
                int bluepixel = 255*(bluetab[x] - minblue)/(maxblue - minblue);

                newpixel[x] = Color.argb(1, redpixel, greenpixel, bluepixel);
            }
            bmp.setPixels(newpixel, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        }

    }

    /// CONTRASTE COULEURS RENDERSCRIPT ///

    /*
    private void ContrasteCouleursDRS(Bitmap bmp) {
        RenderScript rs = RenderScript.create(this);

        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());

        ScriptC_contrasteD contraste = new ScriptC_contrasteD(rs);

        contraste.forEach_GetMinMaxColor(input, output);
        contraste.invoke_createlutred();
        contraste.invoke_createlutgreen();
        contraste.invoke_createlutblue();
        contraste.forEach_Final(input,output);


        output.copyTo(bmp);

        input.destroy();
        output.destroy();
        contraste.destroy();
        rs.destroy();

    }*/


    /// Flou Lissage ///


    /// JAVA VERSION ///



    private void Floulissage(Bitmap bmp, Bitmap newimg){
        for (int x = 1; x < newimg.getWidth()-1; x++){
            for (int y = 1; y < newimg.getHeight()-1; y++ ){
                /// va chercher les valeurs r g b des pixels autours //
                int a =0;
                int b = 0;
                int c = 0;
                for (int x2 = x -1; x2 <= x+1; x2++) {
                    for (int y2 = y - 1; y2 <= y + 1; y2++) {
                        int e = bmp.getPixel(x2, y2);
                        a = a + Color.red(e);
                        b = b + Color.green(e);
                        c = c + Color.blue(e);
                    }
                }


                newimg.setPixel(x,y,Color.argb(1,a/9,b/9,c/9));
            }
        }

    }





}