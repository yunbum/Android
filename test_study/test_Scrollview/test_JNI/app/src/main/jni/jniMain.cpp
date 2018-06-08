//
// Created by 백윤범 on 2018. 4. 11..
//


#include <com_example_ppang_jniexample_MainActivity.h>

JNIEXPORT jstring JNICALL Java_com_example_ppang_jniexample_MainActivity_getJNIString(JNIEnv *env, jobject obj) {

    return env->NewStringUTF("Message from jniMain");
}



