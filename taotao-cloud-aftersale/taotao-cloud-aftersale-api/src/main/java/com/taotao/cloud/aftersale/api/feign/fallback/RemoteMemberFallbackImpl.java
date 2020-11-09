// package com.taotao.cloud.aftersale.api.feign.fallback;
//
// import com.taotao.cloud.aftersale.api.feign.RemoteMemberService;
// import com.taotao.cloud.common.utils.LogUtil;
// import com.taotao.cloud.core.model.Result;
// import com.taotao.cloud.core.model.SecurityUser;
// import feign.hystrix.FallbackFactory;
//
// /**
//  * RemoteLogFallbackImpl
//  *
//  * @author dengtao
//  * @date 2020/4/29 21:43
//  */
// public class RemoteMemberFallbackImpl implements FallbackFactory<RemoteMemberService> {
//     @Override
//     public RemoteMemberService create(Throwable throwable) {
//         return new RemoteMemberService() {
//             @Override
//             public Result<SecurityUser> getMemberSecurityUser(String getMemberSecurityUser) {
//                 LogUtil.error("调用getMemberSecurityUser异常：{}", throwable, getMemberSecurityUser);
//                 return Result.failed(null, 500);
//             }
//         };
//     }
// }
