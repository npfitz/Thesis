#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <math.h>
#include "dcraw.c"


jintArray Java_com_thesis_Image_DecodeImage(JNIEnv* env, jobject thisobject, jstring path){

	jboolean isCopy;
	char *args[2];
	args[0] = "/sdcard/";
	args[1] = (*env)->GetStringUTFChars(env, path, &isCopy);
	LOGINFO("Starting... %s", args[1]);
	unsigned int *image = nick_decode(2, args);

	int h = image[0];
	int w = image[1];


	LOGINFO("Got stuff back");
	LOGINFO("Hopefully.... %d,  %d", h, w);

	jintArray retval;
	retval = (*env) -> NewIntArray(env, (h * w) + 2);

	LOGINFO("Made the array");

	(*env) -> SetIntArrayRegion(env, retval, 0, (h*w) + 2, image);

	free(image);

	LOGINFO("Set Array Values");
	return retval;
}

jbyteArray Java_com_thesis_Image_PullThumb(JNIEnv* env, jobject thisobject, jstring path){

	jboolean isCopy;
	char *args[2];
	args[0] = "/sdcard/";
	args[1] = (*env)->GetStringUTFChars(env, path, &isCopy);
	LOGINFO("Starting... %s", args[1]);
	unsigned char *image = return_thumb(args[1]);

	unsigned int * size = (unsigned int *)image;



	LOGINFO("pulled a thumb!");


	jintArray retval;
	retval = (*env) -> NewByteArray(env, (*size));

	LOGINFO("Made the array");

	(*env) -> SetByteArrayRegion(env, retval, 0, (*size), image);

	free(image);

	LOGINFO("Set Array Values");
	return retval;
}
































