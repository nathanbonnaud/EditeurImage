#pragma  version (1)
#pragma  rs  java_package_name(com.android.rssample)



static int32_t minred = 255;
static int32_t mingreen = 255;
static int32_t minblue = 255;
static int32_t maxred = 0;
static int32_t maxgreen = 0;
static int32_t maxblue = 0;


static float Lutred[256];
static float Lutgreen[256];
static float Lutblue[256];

/// Recupere le MIN et MAX de chaque Couleur R G B ////

uchar4  RS_KERNEL  GetMinMaxColor(uchar4  in) {

  /*
    float4  pixelf = rsUnpackColor8888(in);
    pixelf.r = pixelf.r*255;
    pixelf.g = pixelf.g*255;
    pixelf.b = pixelf.b*255;
    if (pixelf.r < minred){
        minred = pixelf.r;
    }
    if (pixelf.r > maxred){
            maxred = pixelf.r;
        }
    if (pixelf.g < mingreen){
            mingreen = pixelf.g;
        }
    if (pixelf.g > maxgreen{
            maxgreen = pixelf.g;
        }
    if (pixelf.b < minblue){
            minblue = pixelf.b;
        }
    if (pixelf.b > maxblue){
            maxblue = pixelf.b;
        }


    pixelf.r = pixelf.r/255;
    pixelf.g = pixelf.g/255;
    pixelf.b = pixelf.b/255;
    return in;
   }





/// Create un LUT pour chaque couleur R G B ////

void createlutred(){
    for(int nr = 0; nr < 255; nr++){
        Lutred[nr] = (float) ((nr - minred)*255)/(maxred-minred);
    }
}

void createlutgreen(){
    for(int ng = 0; ng < 255; ng++){
        Lutgreen[ng] = (float) (255*(ng - mingreen))/(maxgreen-mingreen);
    }
}

void createlutblue(){
    for(int nb = 0; nb < 255; nb++){
        Lutblue[nb] = (float) ((nb - minblue)*255)/(maxblue-minblue);
    }
}



uchar4  RS_KERNEL  Final(uchar4  in) {

    float4  pixelf = rsUnpackColor8888(in);

    pixelf.r = Lutred[pixelf.r];
    pixelf.g = Lutgreen[pixelf.g];
    pixelf.b = Lutblue[pixelf.b];

    pixelf.r = pixelf.r/255;
    pixelf.g = pixelf.g/255;
    pixelf.b = pixelf.b/255;
    return rsPackColorTo8888(pixelf.r,pixelf.g,pixelf.b,pixelf.a);*/
    return in;
}
