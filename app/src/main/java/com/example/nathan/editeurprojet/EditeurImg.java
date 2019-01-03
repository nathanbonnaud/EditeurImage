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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeur_img);



        ImageView i2 = (ImageView) findViewById(R.id.imageView);
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inMutable = true;
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.index2, options2);

        ImageView i = (ImageView) findViewById(R.id.imageView5);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), R.drawable.index, options);
        i.setImageBitmap(image1);



        //ContrasteCouleursDRS(image1);
        //Floulissage(image1);

        /*
        int largeur = options.outWidth;  /// * 2.6
        int hauteur = options.outHeight;   /// *2.6
        System.out.println("largeur:" + largeur + " hauteur : " + hauteur); */








        /// MENU DEROULANT///


        Spinner myspinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> monadaptater = new ArrayAdapter<String>(EditeurImg.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Monspinner));
        monadaptater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(monadaptater);
        myspinner.setOnItemSelectedListener(this);

        /////

    }

    // CONFIGURATION MENU ///



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        TextView tv = (TextView) findViewById(R.id.taille);
        ImageView i = (ImageView) findViewById(R.id.imageView5);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), R.drawable.index3, options);

        ImageView i2 = (ImageView) findViewById(R.id.imageView);
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inMutable = true;
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.index2, options);
        Long time = System.currentTimeMillis();

        switch (position) {
            case 0:
                tv.setText(" ");
                break;
            case 1:
                ToGrey(image1);
                long timeafter = System.currentTimeMillis() - time;
                tv.setText("temps d'execution Gris image1 = " + timeafter + " ms");
                System.out.println( "temps d'execution Gris image1 = " + timeafter + " ms");

                break;
            case 2:
                ToGrey(image2);
                timeafter = System.currentTimeMillis() - time;
                tv.setText("temps d'execution Gris image2 = " + timeafter + " ms");
                System.out.println( "temps d'execution Gris image2 = " + timeafter + " ms");

                break;
            case 3:
                toGreyRS(image1);
                timeafter = System.currentTimeMillis() - time;
                tv.setText("temps d'execution Gris RS image1 = " + timeafter + " ms");
                System.out.println( "temps d'execution Gris RS image 1= " + timeafter + " ms");

                break;
            case 4:
                toGreyRS(image2);
                timeafter = System.currentTimeMillis() - time;
                tv.setText("temps d'execution Gris RS image2 = " + timeafter + " ms");
                System.out.println( "temps d'execution Gris RS image 2= " + timeafter + " ms");

                break;
            case 5:
                Floulissage(image1);
                break;
            /*case 3:
                //Coloriser(image1);

               // break;
            case 4:
                ColoriserRS(image1);

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
                break;*/
        }
        //i.setImageBitmap(image1);
        i2.setImageBitmap(image2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    /// RENDRE UNE IMAGE GRISE///


    ///JAVA VERSION///


    protected void ToGrey(Bitmap bmp) {


        int [] pixel = new int[bmp.getWidth()*bmp.getHeight()];
        int [] greytab = new int[bmp.getWidth()*bmp.getHeight()];
        bmp.getPixels(pixel,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());

        for (int i =0; i < pixel.length ; i++){
            int Grey = (int)( 0.3 * Color.red(pixel[i]) +0.59 *Color.green(pixel[i])+0.11* Color.blue(pixel[i]));
            greytab[i] = Color.argb(1,Grey,Grey,Grey);
        }
        bmp.setPixels(greytab,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());



        /// Version plus coûteuse ///


        /*
        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++) {
                int a = bmp.getPixel(x,y);
                double Grey = 0.3 * Color.red(a) + 0.59 * Color.green(a) + 0.11 * Color.blue(a);
                bmp.setpixel(x,y,(Grey,Grey,Grey));
            }
        }*/


        ////

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


    private void ContrasteCouleursDRS(Bitmap bmp) {
        RenderScript rs = RenderScript.create(this);

        Allocation input = Allocation.createFromBitmap(rs, bmp);
        Allocation output = Allocation.createTyped(rs, input.getType());

        ScriptC_contrasteD contraste = new ScriptC_contrasteD(rs);

        contraste.forEach_GetMinMaxColor(input);
        contraste.invoke_createlutred();
        contraste.invoke_createlutgreen();
        contraste.invoke_createlutblue();
        contraste.forEach_Final(input,output);


        output.copyTo(bmp);

        input.destroy();
        output.destroy();
        contraste.destroy();
        rs.destroy();

    }


    /// Flou Lissage ///


    /// JAVA VERSION ///



    private Bitmap Floulissage(Bitmap bmp){
        ImageView i = (ImageView) findViewById(R.id.imageView5);
        Bitmap newimg = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig() );

        /// taille du masque appliqué ///


        int n = 10;
        int div = (2*n +1)*(2*n+1);



        int [] pixel = new int[bmp.getWidth()*bmp.getHeight()];
        int [] newpixel = new int[bmp.getWidth()*bmp.getHeight()];
        bmp.getPixels(pixel,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());



        for (int x = n; x < newimg.getWidth()-n; x++){
            for (int y = n; y < newimg.getHeight()-n; y++ ){

                /// va chercher les valeurs r g b des pixels autours //
                int a =0;
                int b = 0;
                int c = 0;
                for (int x2 = x -n; x2 <= x+n; x2++) {
                    for (int y2 = y - n; y2 <= y + n; y2++) {
                        int e = pixel[x2 + (y2*bmp.getWidth())];
                        a = a + Color.red(e);
                        b = b + Color.green(e);
                        c = c + Color.blue(e);

                    }
                }

                newpixel[x + (y*newimg.getWidth())] = Color.argb(255,a/div,b/div,c/div);



            }
        }

        newimg.setPixels(newpixel,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());
        i.setImageBitmap(newimg);
        return newimg;


    }





}