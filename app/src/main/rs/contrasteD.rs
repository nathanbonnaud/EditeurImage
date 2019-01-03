#pragma  version (1)
#pragma  rs  java_package_name(com.android.rssample)



float minred = 255;
float mingreen = 255;
float minblue = 255;
float maxred = 0;
float maxgreen = 0;
float maxblue = 0;


float Lutred[256];
float Lutgreen[256];
float Lutblue[256];

/// Recupere le MIN et MAX de chaque Couleur R G B ////

void  RS_KERNEL  GetMinMaxColor(uchar4  in) {


    float4  pixelf = rsUnpackColor8888(in);
    pixelf.r *= 255;
    pixelf.g *= 255;
    pixelf.b *= 255;

    if (pixelf.r < minred){
            minred = pixelf.r;
        }
    if (pixelf.r > maxred){
            maxred = pixelf.r;
        }
    if (pixelf.g < mingreen){
            mingreen = pixelf.g;
        }
    if (pixelf.g > maxgreen){
            maxgreen = pixelf.g;
        }
    if (pixelf.b < minblue){
            minblue = pixelf.b;
        }
    if (pixelf.b > maxblue){
            maxblue = pixelf.b;
        }




   }





/// Create un LUT pour chaque couleur R G B ////

void createlutred(){
    for(int nr = 0; nr < 256; nr++){
        Lutred[nr] = (float) ((nr - minred)*255)/(maxred-minred);
    }
}

void createlutgreen(){
    for(int ng = 0; ng < 256; ng++){
        Lutgreen[ng] = (float) (255*(ng - mingreen))/(maxgreen-mingreen);
    }
}

void createlutblue(){
    for(int nb = 0; nb < 256; nb++){
        Lutblue[nb] = (float) ((nb - minblue)*255)/(maxblue-minblue);
    }
}



uchar4  RS_KERNEL  Final(uchar4  in) {

    float4  pixelf = rsUnpackColor8888(in);

    float red = Lutred[(int) (pixelf.r*255)]/255;
    float green = Lutgreen[(int) (pixelf.g*255)]/255;
    float blue= Lutblue[(int) (pixelf.b*255)]/255;


    return rsPackColorTo8888(red,green,blue,pixelf.a);

}