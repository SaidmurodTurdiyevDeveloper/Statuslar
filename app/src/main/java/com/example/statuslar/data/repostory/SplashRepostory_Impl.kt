package com.example.statuslar.data.repostory

import com.example.statuslar.domen.repostory.SplashRepostory
import com.example.statuslar.data.source.local.room.MyDatabase
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.data.source.local.shared.LocalStoradge
import com.example.statuslar.zZz_utills.DefaultSpeachLists
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashRepostory_Impl @Inject constructor(
    database: MyDatabase,
    private var localStoradeg: LocalStoradge
) : SplashRepostory {
    private var dao = database.getSplashdao()
    private var speachlists = DefaultSpeachLists()

    override suspend fun isFirst(): Flow<Boolean> = flow {
        emit(localStoradeg.getFirst() == 1)
    }

    override suspend fun getEmptyWiseIdSpeches(): Flow<List<SpeachEnity>> = dao.getEmptyWiseManIdSpeaches()

    override suspend fun deleteEmpty(list: List<SpeachEnity>): Int = dao.deleteAll(list)

    override suspend fun setFirst(cond: Boolean) = if (cond) localStoradeg.setFirst(1) else localStoradeg.setFirst(0)

    override suspend fun loadData() {
        /*
       * Alisher Navoiy
       * */
        val alisher = dao
            .insert(
                WiseManEntity(
                    0,
                    "Alisher Navoiy",
                    "# Alisher",
                    "Alisher Navoiy (1441-1501) - buyuk shoir va mutafakkir, davlat arbobi. To‘liq ismi Nizomiddin Mir Alisher. Navoiy tahallusi ostida chig‘atoy (eki o‘zbek tili) hamda forsiyda (fors tilidagi asarlarida) ijod qilgan. G‘arbda chig‘atoy adabiyoti hisoblanmish o‘zbek adabiyotining eng yirik namoyondasi.",
                    "",
                    1
                )
            )
        dao.insertAll(speachlists.getAlisherNavoiy(alisher))
        /*
        * Bruce Lee
        * */
        val brusli = dao.insert(
            WiseManEntity(
                0,
                "Bruce Lee",
                "# Bruce",
                "Bruce Lee (taxallusi; toʻliq nomi Li Syaolun; xitoycha \"bryusli\" soʻzi \"kichik ajdaho\" maʼnosini anglatadi), (1940.27.11, San-Fransisko – 1973.20.7, Hong Kong) — jang sanʼati (kunfu) ustasi",
                "",
                3
            )
        )
        dao.insertAll(speachlists.getBrusli(brusli))

        /*
        * Albert enishteyn
        * */
        val albert = dao.insert(
            WiseManEntity(
                0,
                "Albert Einstein",
                "# Albert",
                "Albert Einstein (talaffuz etilishi (yordam·maʼlumot); 14-mart, 1879 — 18-aprel, 1955) — nemis va yahudiy fizik-nazariyotchisi",
                "", 2
            )
        )
        dao.insertAll(speachlists.getAlbertEnishteyn(albert))

        /*
        * Islom Karimov
        * */
        val islom = dao.insert(
            WiseManEntity(
                0,
                "Islom Karimov",
                "# Islom",
                "Islom Abdugʻaniyevich Karimov (1938-yil 30-yanvar, Samarqand shahri — 2016-yil 2-sentabr, Toshkent shahri) — davlat va siyosat arbobi, Oʻzbekiston Respublikasining birinchi prezidenti",
                "",
                8
            )
        )
        dao.insertAll(speachlists.getKarimov(islom))

        /*
        * Muxammad Ali
        * */
        val muhammad = dao.insert(
            WiseManEntity(
                0,
                "Muhammad Ali",
                "# Muhammad",
                "Muhammad Ali (Cassius Marcellus Clay Jr. 17.01.1942 — 03.06.2016) — amerakalik professional bokschi va hayriyachi.20- asrning eng taniqli sport arboblaridan va eng buyuk bokschilaridan biri sifatida tanilgan",
                "",
                5
            )
        )
        dao.insertAll(speachlists.getMuxammadAli(muhammad))

        /*
        * Steave Jobs
        * */
        val steave = dao.insert(
            WiseManEntity(
                0,
                "Steve Jobs",
                "# Steven",
                "Steven Paul Jobs (talaffuzi: Stiven Pol Jobs) yoki Steve Jobs (talaffuzi: Stiv Jobs)(24-fevral 1955 — 5-oktabr 2011) — amerikalik muhandis va tadbirkor, Apple Inc. korporatsiyasi asoschisi va direktori boʻlgan",
                "",
                9
            )
        )
        dao.insertAll(speachlists.getSteaveJobs(steave))
    }
}