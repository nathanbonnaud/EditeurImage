#pragma  version (1)
#pragma  rs  java_package_name(com.android.rssample)


int rand1;

uchar4  RS_KERNEL  toColor(uchar4  in) {
    float4  pixelf = rsUnpackColor8888(in);

    int T;
    float S;
    float V;
    T =rand1 ;

    if (pixelf.r ==0 && pixelf.g ==0 && pixelf.b ==0){
       S=0;
    }else{
       S = 1 -(min(min(pixelf.r,pixelf.g),pixelf.b)/(max(max(pixelf.r,pixelf.g),pixelf.b)));
    }
    V = (max(max(pixelf.r,pixelf.g),pixelf.b));


    int t;

    if ((T/60) > 0){
       t = T/60 % 6;
    }else{
       t= -(T/60) %6;
    }

    int f = (T/60) - t;

    float l = V * ( 1- S );
    float m = V * ( 1 - f * S );
    float n = V * ( 1 - ( 1 - f ) * S );

    if (t == 0){
        pixelf.r = V;
        pixelf.g = n;
        pixelf.b = l;
        return rsPackColorTo8888(pixelf.r,pixelf.g,pixelf.b,pixelf.a);
    }else if (t == 1){
        pixelf.r = m;
        pixelf.g = V;
        pixelf.b = l;
        return rsPackColorTo8888(pixelf.r,pixelf.g,pixelf.b,pixelf.a);
    }else if (t==2){
        pixelf.r = l;
        pixelf.g = V;
        pixelf.b = n;
        return rsPackColorTo8888(pixelf.r,pixelf.g,pixelf.b,pixelf.a);

    }else if (t==3){
        pixelf.r = l;
        pixelf.g = m;
        pixelf.b = V;
        return rsPackColorTo8888(pixelf.r,pixelf.g,pixelf.b,pixelf.a);
    }else if (t==4){
        pixelf.r = n;
        pixelf.g = l;
        pixelf.b = V;
        return rsPackColorTo8888(pixelf.r,pixelf.g,pixelf.b,pixelf.a);

    }else if (t==5){
        pixelf.r = V;
        pixelf.g = l;
        pixelf.b = m;
        return rsPackColorTo8888(pixelf.r,pixelf.g,pixelf.b,pixelf.a);
    }

}