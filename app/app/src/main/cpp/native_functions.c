#include <jni.h>
#include <string.h>

JNIEXPORT jstring JNICALL
Java_com_example_projectx_NativeLib_getCountryCapital(JNIEnv *env, jobject thiz, jstring country) {
    const char *input_country = (*env)->GetStringUTFChars(env, country, 0);

    if (strcmp(input_country, "France") == 0) {
        (*env)->ReleaseStringUTFChars(env, country, input_country);
        return (*env)->NewStringUTF(env, "Paris");
    }

    (*env)->ReleaseStringUTFChars(env, country, input_country);
    return (*env)->NewStringUTF(env, "Unknown");
}
