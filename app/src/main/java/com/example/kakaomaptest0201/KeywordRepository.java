package com.example.kakaomaptest0201;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeywordRepository {
    private Entity_Keyword entityKeyword;
    /* 생성자를 통해 레트로핏 객체를 가져옴 */

    public KeywordRepository() {
        entityKeyword = NetworkManager.getInstance().getRetrofit().create(Entity_Keyword.class);
    }

    public void retrieveData(MainActivity mainActivity, Double latitude, Double longitude){
        //YOUR_RSET_API_KEY 에 REST_API_KEY를 넣어줘야함.
        Call<Meta.KeyWord> call = entityKeyword.searchKeyword("KakaoAK c31053a1bc2251aed8d80f9c8cd29664","로또 판매점",latitude,longitude,2000);
        call.enqueue(new Callback<Meta.KeyWord>() {
            @Override
            public void onResponse(Call<Meta.KeyWord> call, Response<Meta.KeyWord> response) {
                if(response.isSuccessful()){
                    Log.d( "Success","데이터 받아오기 성공");
                   mainActivity.retrieveOnSuccess(response.body().getDocuments());
                }
                else{
                    Log.e( "Fail","서버 통신 실패");
                }
            }

            @Override
            public void onFailure(Call<Meta.KeyWord> call, Throwable t) {
                Log.e( "Fail","서버 통신 실패");
            }
        });
    }
}
