#pragma  version (1)
#pragma  rs  java_package_name(com.android.rssample)

static  const  float4  weight = {0.299f, 0.587f, 0.114f, 0.0f};

uchar4  RS_KERNEL  toConserve(uchar4  in) {
    float4  pixelf = rsUnpackColor8888(in);
    const  float  grey = dot(pixelf , weight);
    if (pixelf.g <= 150 && pixelf.b <= 150 && pixelf.r> pixelf.g && pixelf.r>pixelf.b){
     return rsPackColorTo8888(pixelf.r,pixelf.g,pixelf.b,pixelf.a);
    }else{
     return rsPackColorTo8888(grey,grey,grey,pixelf.a);
     }
}