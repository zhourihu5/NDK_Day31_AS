#include <jni.h>
#include <string>
#include "BitmapMatUtils.h"
#include <android/log.h>
#include "cardocr.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_darren_ocr_BankCardOcr_cardOcr(JNIEnv *env, jclass type, jobject bitmap,jstring path_) {
    __android_log_print(ANDROID_LOG_ERROR, "TAG", "Java_com_darren_ocr_BankCardOcr_cardOcr");

    // 1. bitmap -> mat
    Mat mat;
    BitmapMatUtils::bitmap2mat(env, bitmap, mat);

    //  轮廓增强（梯度增强）
    Rect card_area;
    co1::find_card_area(mat,card_area);
    // 对我们过滤到的银行卡区域进行裁剪
    __android_log_print(ANDROID_LOG_ERROR, "TAG", "找到了,%d",card_area.width);
    Mat card_mat(mat,card_area);
    const char* path=env->GetStringUTFChars(path_,0);
//    "/sdcard/card_n.jpg"
    imwrite(path,card_mat);
    env->ReleaseStringUTFChars(path_,path);
    return env->NewStringUTF("622848");
}