package com.example.homwwork4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.homwwork4.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var intentData: Stuff
    lateinit var data: Stuff
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = intent.getParcelableExtra<Stuff>("data")!!
        intentData = data
        Log.d("d2", "$data, $intentData")
        init()
//        for(i in 0..stuffList.size-1){
//            Log.d("정보 확인", stuffList[i].pullIntroduce())
//        }
        setContentView(binding.root)
    }

    private fun init() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        binding.imgDetailStuffImage.setImageResource(data.pullImg())
        binding.tvDetailCeller.text = data.pullCeller()
        binding.tvDetailAddress.text = data.pullAddress()
        binding.tvDetailPrice.text = data.priceCount()
        binding.tvDetailStuffName.text = data.pullName()
        settingLikeButton(binding.imgDetailLike, intentData)
        for(i in 0..<stuffList.size){
            if(data.pullCeller() == stuffList[i].pullCeller()){
                binding.tvDetailStuffIntroduce.text = getText(introList[i])
            }
        }
        binding.imgDetailBackPress.setOnClickListener {
            val intent = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        // 넘어온 데이터를 물품 리스트(stuffList)에서 찾고 해당 물품이 관심 목록(userLikeList)에 없으면
        // 버튼을 누를 시 해당 물품에 대한 정보를 관심 목록에 추가 및
        // 버튼이 빈 하트에서 채워진 하트로 되게끔 설정
        // 만약 해당 물품이 관심 목록에 있으면 관심 목록에서 제거 및
        // 채워진 하트에서 빈 하트로 되게끔 설정
        // 좋아요 수는 Stuff 내부에 설정한 메소드로 변경됨
        binding.imgDetailLike.setOnClickListener {
            stuffList.forEach {
                if(intentData.pullName() == it.pullName()){
                    if(!userLikeList.contains(it)){
                        it.addLike()
                        userLikeList.add(it)
                        it.isInterest()
                        binding.imgDetailLike.setImageResource(R.drawable.icon_fillheart)
                        Toast.makeText(this, "관심 목록에 추가되었습니다", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        it.decreaseLike()
                        userLikeList.remove(it)
                        it.isNotInterest()
                        binding.imgDetailLike.setImageResource(R.drawable.icon_heart)
                        Toast.makeText(this, "관심 목록에서 삭제되었습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnDetailComments.setOnClickListener {
            val intent = Intent(this@DetailActivity, ChatActivity::class.java)
            intent.putExtra("chatData", intentData)
            startActivity(intent)
        }
    }
    // 해당 물품이 관심 목록 리스트(userLikeList)에 있으면 채워진 하트로, 아니면 비워진 하트로 이미지 아이콘 설정
    private fun settingLikeButton(img: ImageView, data: Stuff){
        for(i in 0..<stuffList.size){
            if(stuffList[i].pullName() == intentData.pullName()){
                if(stuffList[i].pullInterest()){
                    img.setImageResource(R.drawable.icon_fillheart)
                }
                else
                    img.setImageResource(R.drawable.icon_heart)
            }
        }
    }
}