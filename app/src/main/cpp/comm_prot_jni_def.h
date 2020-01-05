#ifndef __COMM_PROT_JNI_DEF_H__
#define __COMM_PROT_JNI_DEF_H__

#ifdef __cplusplus
extern "C" {
#endif//__cplusplus
/*<----------body---------->*/
#include "jni.h"

/**
 * global signatures of business
 */

// FID_NAME_XXX (jobject field aka complex object field only)

// METHOD_SIG_XXX
#define METHOD_SIG_test_void "()V"

// CLASS_PATH_XXX
#define CLASS_PATH_String "java/lang/String"

/**
 * global external variables for business (read-only!!!) 
 */
// jclass related
extern jclass g_jclass_String;
// jobject related
// extern jobject jCommProtocol;

/**
 * global external variables for JNI environment
 */
extern JNIEnv *getJniEnv(void);

extern void setJniEnv(JNIEnv *env);

extern void setJniVm(JNIEnv *env);

extern JavaVM *getJniVm(void);
/*<----------body----------/>*/
#ifdef __cplusplus
}
#endif//__cplusplus

#endif//__COMM_PROT_JNI_DEF_H__