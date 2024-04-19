package com.example.homwwork4

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.homwwork4.databinding.ActivityMainBinding
val stuffList = mutableListOf<Stuff>(
    Stuff("산진 한달된 선풍기 팝니다", "서울 서대문구 창천동", R.drawable.sample1, 1000, "대현동"),
    Stuff("김치냉장고", "인천 계양구 귤현동", R.drawable.sample2, 20000, "안마담"),
    Stuff("샤넬 카드지갑", "수성구 범어동", R.drawable.sample3, 10000, "코코유"),
    Stuff("금고", "해운대구 우제2동", R.drawable.sample4, 10000, "Nicole"),
    Stuff("갤럭시Z플립3 팝니다", "연제구 연산제8동", R.drawable.sample5, 150000, "절명"),
    Stuff("프라다 복조리백", "수원시 영통구 원천동", R.drawable.sample6, 50000, "미니멀하게"),
    Stuff("울산 동해오션뷰 60평 복층\n 펜트하우스 1일 숙박권\n 펜션 힐링 숙소 별장", "남구 옥동", R.drawable.sample7, 150000, "굿리치"),
    Stuff("샤넬 탑핸들 가방", "동래구 온천제2동", R.drawable.sample8, 180000, "난쉽"),
    Stuff("4행정 엔진분무기 판매합니다", "원주시 명륜2동", R.drawable.sample9, 30000, "알뜰한"),
    Stuff("셀린느 버킷 가방", "중구 동화동", R.drawable.sample10, 190000, "똑태현")
)
val introList = mutableListOf<Int>(
    R.string.item1_introduce,R.string.item2_introduce,R.string.item3_introduce,R.string.item4_introduce,
    R.string.item5_introduce,R.string.item6_introduce,R.string.item7_introduce,R.string.item8_introduce,
    R.string.item9_introduce,R.string.item10_introduce
)
val userLikeList = mutableListOf<Stuff>()
val chatData = mutableListOf<Chat>(
)
const val LIKE = 1
const val ENTIRE = 0
var isInputChatData: Boolean = false
var currentList : Int = ENTIRE

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        for (i in 0..<stuffList.size) {
            val text = getText(introList[i]).toString()
            stuffList[i].pushIntroduce(text)
        }
        super.onCreate(savedInstanceState)
        init()
        listDataChange()
        setContentView(binding.root)
    }

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        if(currentList == ENTIRE) {
            binding.recyclerList.adapter = StuffViewAdapter(stuffList, object : onClickEvent {
                override fun onItemClick(item: Stuff) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("data", item)
                    Log.d("d1", "$item")
                    startActivity(intent)

                }

                //삭제하기
                override fun onRemoveItem(item: Stuff) {
                    showDeleteDialog(item)
                }
            })
        }
        else {
            binding.recyclerList.adapter = StuffViewAdapter(userLikeList, object : onClickEvent {
                override fun onItemClick(item: Stuff) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("data", item)
                    Log.d("d1", "$item")
                    startActivity(intent)

                }

                //삭제하기
                override fun onRemoveItem(item: Stuff) {
                    showDeleteDialog(item)
                }
            })
        }
        binding.recyclerList.layoutManager = LinearLayoutManager(this)
        if (currentList == ENTIRE) {
            binding.tvBtnChoice.text = getText(R.string.listTransForm)
        } else {
            binding.tvBtnChoice.text = getText(R.string.listRollBack)
        }
        val scrollListener = object : RecyclerView.OnScrollListener() {
            //리싸이클러 뷰를 담는 nestedscrollview의 높이가 0보다 커지면 버튼 활성화
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (binding.scrollContainer.scrollY > 0) {
                    binding.btnUp.visibility = View.VISIBLE
                } else {
                    binding.btnUp.visibility = View.GONE
                }
            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if(dy > 0) {
//                    Log.d("스크롤 높이", "${dy}")
//                }
//            }
        }
        //위로가기 버튼을 누를 시 nestedscrollview의 높이를 최상단으로 이동시키기
        binding.recyclerList.addOnScrollListener(scrollListener)
        binding.btnUp.setOnClickListener {
            binding.scrollContainer.scrollY = 0
        }
        if (!isInputChatData)
            inputChatData()

    }

    //다이얼로그 출력,, positiveButton클릭 시 리스트에서 해당 객체를 삭제 후 리싸이클러뷰 어댑터에 데이터 갱신 요청
    @SuppressLint("NotifyDataSetChanged")
    private fun showDeleteDialog(item: Stuff) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.delete_title)
            .setMessage(R.string.delete_message)
            .setIcon(R.drawable.icon_caution)
            .setPositiveButton("YES") { dialog, which ->
                stuffList.remove(item)
                if(userLikeList.contains(item)){
                    userLikeList.remove(item)
                }
                binding.recyclerList.adapter!!.notifyDataSetChanged()
            }
            .setNegativeButton("NO") { dialog, which ->

            }
            .create()
            .show()
    }

    private fun inputChatData() {
        var text = ""
        val receiver = listOf<Int>(
            R.string.receiver_message_1,
            R.string.receiver_message_2,
            R.string.receiver_message_3,
        )
        val sender = listOf<Int>(
            R.string.sender_message_1,
            R.string.sender_message_2,
            R.string.sender_message_3,
        )
        for (i in 0..2) {
            text = getText(sender[i]).toString()
            chatData.add(Chat(text, SENDER))
            text = getText(receiver[i]).toString()
            chatData.add(Chat(text, RECEIVER))
        }
        isInputChatData = true
    }

    @Deprecated("Deprecated in Java")
    //앱 종료 메세지 만들기,
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.backPressTitle)
            .setMessage(R.string.backPress)
            .setIcon(R.drawable.icon_caution)
            .setPositiveButton("YES") { dialog, which ->
                super.onBackPressed()
            }
            .setNegativeButton("NO") { dialog, which ->
            }
            .create()
            .show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun listDataChange() {
        binding.tvBtnChoice.setOnClickListener {
            if (currentList == ENTIRE) {
                binding.recyclerList.adapter =
                    StuffViewAdapter(userLikeList, object : onClickEvent {
                        override fun onItemClick(item: Stuff) {
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra("data", item)
                            Log.d("d1", "$item")
                            startActivity(intent)
                        }

                        override fun onRemoveItem(item: Stuff) {
                            showDeleteDialog(item)
                        }
                    })
                binding.recyclerList.layoutManager = LinearLayoutManager(this)
                (binding.recyclerList.adapter as StuffViewAdapter).notifyDataSetChanged()
                currentList = LIKE
                binding.tvBtnChoice.text = getText(R.string.listRollBack)
            } else {
                binding.recyclerList.adapter =
                    StuffViewAdapter(stuffList, object : onClickEvent {
                        override fun onItemClick(item: Stuff) {
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra("data", item)
                            Log.d("d1", "$item")
                            startActivity(intent)
                        }
                        override fun onRemoveItem(item: Stuff) {
                            showDeleteDialog(item)
                        }
                    })
                binding.recyclerList.layoutManager = LinearLayoutManager(this)
                (binding.recyclerList.adapter as StuffViewAdapter).notifyDataSetChanged()
                currentList = ENTIRE
                binding.tvBtnChoice.text = getText(R.string.listTransForm)
            }
        }
    }
}




