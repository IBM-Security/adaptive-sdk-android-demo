package com.ibm.security.a2

import android.content.Context
import com.ibm.security.adaptivesdk.AdaptiveCollectionService
import com.trusteer.tas.TAS_CLIENT_INFO
import com.trusteer.tas.tasWired.*

/**
 * A structure storing the vendor ID, client ID, and client key of a Trusteer client used to
 * initiate the collection process.
 *
 * Useful for the [AdaptiveContext.start] method.
 *
 * @param vendorId The identifier of the Trusteer vendor.
 * @param clientId The identifier of the Trusteer client.
 * @param clientKey The key of the Trusteer client.
 *
 * Example usage:
 * ```
 * val trusteerCollection = TrusteerAdaptiveCollection("hcu.com", "hcu.bankingapp", "YMAQAABN...CNC4JULI")
 * ```
 */
class TrusteerCollectionService: AdaptiveCollectionService {
    /** The identifier of the vendor. */
    override val vendorId: String = "ibmemployeesusnapr.com"
    /** The client identifier. */
    override val clientId: String = "ibmemployeesusnapr.bankingapp"
    /** The client key. */
    override val clientKey: String = "YMAQAABNFUWS2LKCIVDUSTRAKBKUETCJIMQEWRKZFUWS2LJNBJGUSSKCJFVECTSCM5VXC2DLNFDTS5ZQIJAVCRKGIFAU6Q2BKE4ECTKJJFBEGZ2LINAVCRKBOBYHU4SLGVYVGL2XOVEGU6CZKQVVQMSDBJLHU2DBOU2FQ5LWJZGEKVZPKY4VEWLOPJTWMNSCHF4UMUSELJKDA4RXMRVGW4KIJ5NEKR2PKNBDIWTBF5IVETSMNRJWIK2YJJKCW3TTBJSXS4LMN5SGWN3NGU4G6NTILI3VE6CTNFQTC4DOF5ZEEULZM42DE32WMRLXC3CPOR2EW5LBGV3VSK2XONJUEV22J54CWULNMZFWCR3TBI4XCUDOMVAUI43EIJAW22KKOFWHMSRULFFDS2CPKRJFKL2CMRVVO4KMIJJCWMRUFNJXKSBZIFFDCZZVGRGVA5DGGZTDCMKKJAZGI4TMBJMWKUCTJFYGCQLNMFHWGQRVGBHEO22EF5BEG4TDIQYUG6KQHBZHOM2RIM3HSZKZGVCGYUDGOVTTS4LNKJCXEUSHGFRXQUTZKVEDE6CZBJAVG3DLGVWFURRYPFLDI2S2GBDUMOLCOM3HUTBWONYTQM2JLJBU2MCOJ5LGUYKFJZEDIYTRNZSVSTTBPAXTOYLTNNTU6QJWOZEXO3TCBJKFCSKEIFIUCQQKFUWS2LJNIVHEIICQKVBEYSKDEBFUKWJNFUWS2LIKAMAAAAAAAAAAAAAAAAABQAAAABZGG2LOM5PWSYTNMVWXA3DPPFSWK43VONXGC4DSAQBAAAABAAAAAAIAAAADICYCAAOAAAAANB2HI4DTHIXS6OJRGQ4DALTOFZ2HE5LTORSWK4ROMNXW22AOAAAE2SKJJN4VCSKCIF5EGQ2DN44EOQ2TOFDVGSLCGNCFCRKIIFQUGQ2DN5AUKZ3HOA4E2SKJJNSUIQ2DIJJTQR2DKNYUOU2JMIZUIUKFJBBHCQ2DIJJUC53HM5KWGQLHIVAU2SKJIZDFCWKKJNXVUSLIOZRU4QKRMNBE2QTXI5BWS4KHKNEWEM2EKFCU2QKRLF3UIZ2RJEZFERLZGZ5FGUT2GA4EGQLHM5AWOSKJIU3EK53YPFBWY32QLFJE63RXKFGHS522GZXXAUCRMNMUOY3RKBDDSQTGGZ5EGUKZKUVXITKRLBUTGVCNJ44S6ZTNIVGDKN2NHBYG2USRNNNFM2DZKU2DISS2KZVCWT22FNZUKSZZGFUWGUJVK5NFKNSSGAXS6SDWJRGVU4LDIMYG6MSFIFIUQOCJJU4XQYSFOV3WCRKNM5KXKRBQGRWHOMLFMZETM6JPOVMG4YJYKRDTQS32LJCFMT3FOBMUUOCTJFTUIWTLOMXTEMKEJFKEKWRLNJXGIWCLJU3XM3CXNN3GSNCNF5GUWMDYNFCGSZJVMNUEYYRRHBYXU2LQOJBWK2DJMVBHMWRSKNTUQ23DPBVW2Y3XNNWESM3FGNMTCVTCGZTE64LKKFIGIM2VF5SHIV2ZGFKWEY3NIFHVGKZQKBCGQTD2MVUFAZ2RLI2VINLTJN2FOZCEMREE45TYOZIDK3KKHF2HMRKYKJRFMU3SJVBHKULDGN4VIRCPG5IGCZLENJAXE53PKI4W4MLBJFIWG5TUNB2XQ2CKIN2VEVJVJNVXAV2FJFIUUS3HKA3XANJVJJTUQUTRKBZHMN2UKZ2VKYRROVYWYR2HMFJW6T3XK5RUMR3VPJCEWRLCHA2TGSRPOVXW6SDQLEZDOVLWIV3FKQ3UNRGFM6BTIFTUUTJYHFIVKVCIGN3XIWKXJE4FMUCFLFUHMV3RI42UER2SJ5SVSN3WGJUDSVCIKJDEKWKGGFRHAYSLIRXTCWTMNNBUURSFMNXGY5CDGB2U45KQKZETIZZSGJHTGWTXGYVTO6TKKRUW4VCOKNXTK2SYKRMXMQ2IIUYS62SELFTFIT2LKJFXGWLKPFKFGNSIIU2TCUBPJ5EDONDRNZWGORRXMJDE6NS2PJATSY32GRSGIMTXJVWESZCYNEVTSSZXKZFECU2ZIEXUCVRRNVUS6UCXLFGHIYSGHAXXOVCKJ5DDQ52DOMYDKRKWJBBFU2CTGFXU26TBJZ2DSUTQO5RFQQ3PLBYGY43UJJSDQVTONVMGIRTVIJSUSNLRKM2FSTDZN42VAZ3YJZEECSLEMNQWSZ3NGJ2TIM2MJFHWG3SNIM4UQ33WIVXUYSLDNRXHSMTRGNUG6NTFNJIUET3UF5VEKNLZNYZDQNCOJJMFUOLOKFTUQRTDOB2XMOBXKNNHM6SOPFAUO42GKE4TKWKOFNDUQ422GRSEUSDXKN2WGU2WKJSUC4TTMFFSWVTXJZJVI5JLK5GXMWTTINCDMSCDGFYFMMSCNBDGS4DQJ5WXGQTJOE2DKMTUKRUEC6SVO5KVAR2TI5YDEUSVKJRFSUBUJJEEU2CHKFKG6K3UIFVW46SXMNXFG53IJ5GEKS3PNEYGYVJUGNSXETBXJNSGGQKEONJTEQSLI53GM23TMJZS6YKMPAZGI5BZJZFUIRBLJZYEKRCXJRYGMSTRJN4G6UZXHE4WKSBSLJXDANZQONZGWZLNK5BDKRKIM54XAR2ZMJBXGZKLI5TUONZQLE2EOR2KNBSTIYSPLFRTOZDYIJEFUSRSMVLFI2DZJFBDGR3CKZCW2VRTJNTGYV2FMZXU45KGOY3WU6KROZ4UYS2RMFQWYM3RNRLFA5TKJRAXQ23JJZSE45TMJ5FUQQ2SGRTUMK3INBLDMMDRG5EHM3TGNU3VAZZXJNYHGSCDONJDEWCJJJSFQWRVIE3FG5CIJFHHU2BLLJCGOU22IVLHUQLEN54DO6RPINLUKTZVIZCE4T2CF44TCWRYFNIVIRTVGRXVOMCCJJKXIV3SNVEHAQJRKFYXSMDCNNDXS3DLNNITKSCWGY2EQMTEK42DQ4RQN5CWWUTVPEZESMBUNBGHANKDG43GS42IIJ3TOOLMOJDES33BIR4EE5LUF43VUULFIJSU2SCVJVSGOUTLGBGFGQ3SMFRUWVCRNRTGQQ3PJ5TVGMDHFNAWCMC2KVYXGUJPPJHFI42FMRNE26SBNBLXKULPOBITMSCEGVTUY3KVMY2SWNSFI5KEWVJZIJJEMRC2GNVXA2DBPFLHIODDNUZFMYJTKVCVU43MKQXTMVKJPBXGKQTSPJBFIRLHF5TTMOLCMRVUCWTKNFEVU6DIIMZUMVRLIREHK6DWJJFTMWSLNF3DKZDYMZBEMRSRNQ2HE23KKVNDQU2INB3E6TBQI5JTAR3TOFNCW2TXF4ZTQTJLONDUUM3FMZMUEK3WNB4HQ6LOHE4XOOKCGJCWEYLOJNWHUSJRKVZVM3LZIVVTSKZUKZZEIQLLGZUFSQRUGB2HMMJSHF3UYZCRLBIWE4LXIVZS6Y2VI4VVG5RQFNZFCU3KPJWXM3KWMM2VQ2THI5MWQ53GIJ4UE6RYMVEUGQSQIZREQ6KPJVEXKZ2MNFTGG2S2KNKEGNTJNFMCWQ3XJ5EWESLIN5YGWL2GII3FETDSHF4UQWSQJA2EKZDBOZVXU5CJGBSHGL2MMFGESUBZOZFWMMBVKB4UMNSZGBTVMUCIMNCVMK2XJRDHEWDGOA3UWWBPPFIDAZJVKQ2XMSKJIMZGK33BMIZEW4C2JVZW6WSPPJUUYOJTIZZVKQSELJYWW6JUNI2GYVLXGQZXST2COJQWM3JPIZMUEY2ZHEYGI4LHMVAVCSDTMVGE6ULGHAVVOYLZKEXXEODIHFDHAVTPOMXVSTDWLBUTS53QNZYVCT3YJ5QU2SKJIZIVCWKKJNXVUSLIOZRU4QKRMNBG6SKJIZGWOU2DIJJTI53HM5KXCTKJJFDEUZ2ZJRFW6WSJNB3GGTSBKF3UWQKRJNTWOZ2UOVGUSSKFGZVECY2CM5XXC2DLNFDTS5ZQIJCECRKEJVATIRKDIZATGYLZLJ3WS2BVIZAWOSKJIFAVGQ2CJVTUQ32MIR4DMOKQOM4UGT3LOR2XKQTXJJ4TQ6TTIF5FGUKYKZZTSRJLKBSTC2LEG5FE23SBHF3GS2TBHAYGM4DYMFXWC3CPOZLUMWS2LJ4FIZ3HIZFVI5SWGBYGQU2VKB4HQ52EHBFGYRTRJRWFQUTMNZTWK5TZGF3EUWTKG5ETQ2TDO5SVG3TVGY4DIV3RMNYW6RDPFNQVUNSNJFRWCY3BGJ4HG4TQJY4UK6TZHFEVEWJSNRWVOMCXKR4XOU2PGBNEGOLTJBUXISKHONQUC5SKKNJHCSLXKRUGKQKDKAZW24DHPBWEW6JSKJDFM33QGFIDM6SEORBXITSDNIYEYZDJGJLFGYRLIJLTS4KPLAZXQUSGNNDFU2CEJFDUK2CGGE3HGSTEPJXTOWRVNRWUSMLHF52W2MLPIFBVATZTOYYXOS2EFNRDEUKVNZLG4VD2LBTDMVLOLBCWCR3LINHTCZBWNBIXGR2GIVMSWRSDNBSEQZLBJJLUQZLSJFAVENLFKJVDS4D2GBVVCUTVOZBXG4LHM43VKSJYGZUTKL32KIYDGT3GNRVFK4LGGVZWK4SKNFYWOV2HGV2HISLDGQ3VG23JKFZE2NTMIUYXCVDUFNUGY2BUKNYUERCZGFVEUQRYKJUE2T3PIY2XI5DFJRIWWTDROFQXMUDFHFJG22CJGNTFOULIHBZUE5DMF5REMSBXJV2SW3RWJRDDCZJLNRDW642XKFXTKSCPJU3DQ53SKVRWUQ3QGE2EW3JRKMZXSSBZNEXVAZBPINQUG2SKNF2VKWBLOVKDMZ3QKY3U4UZQJFIUWRCIINAXOWSUKM2WI5ZTJRIFETCRM5JTG5ZTJV4VU6SIMFATMULFIYZHSQ2PJJZDSRDQKRJVQZJLNFZVARLCMZTWU4TBFMXTI5DHKRJEKMTJNQ3FMZ22NRUVQVTOPJZGG53FPAZEQ4KXNFJU42CQHFMTC53RGVHFMULMK5KVCNTINY4FU4TDNVYGS2RLMRTHIQ3XNN4HKTSTORIGW6LOIU3DG6TQKFKEW6LTJJTWGVLEOFXWSSTYKEYDA3CHINVGCQJRJNXG6YTDNU2W6STZIVAWY5TIPFIEKR3IMJTGCUSWORVTSMDPHFCGCRSXNU3GEWRWKU3FCS3NINTWOYKQLBTGG3JWJRKFS3LXGN2VEWCXGJ3HGRBQIN3FARCLIE4WSYLQIRRW253LNRBCW32DKEXXGMDTKNHTCZBZMJZXQ3DGNEYUOSJRGVUEC6CVGNLFSVKXLJIHC3LLGVCUQWSWOFMFUOBXGBVHAZDWGZKTG5TJPIYGUVCGKNVDSNTVMVLTMU3JI5ZHMOLPGJTXGNLZLBFFAN2JMJKGCRLQF54DEWRRI5BFE4CGNB4VKMKPIFWUKN2PN5YDQY3UNNUGS32BJR4DGNBUONRGKN2DNNGTM2RYF5HDEML2I5CUSS3SKVRGSMCIJFTG4NRQJNRHAZTHNBCUUVSTMZHWWTJVOZ2XMRZSKNFWORKSI5IHMY3NG5FTM32ZGQVWYN2VKI2FMU2IMN4W6WKSPB2GUWBPMZXESL2KK5EUYWRUJNNDCZTYKJ3TEZDGJBVXQULKMVCHSR3QMFTXUM3GJB2XCUSCJJVG6ULOMNKFMZSMGMYEITTLORQTK3LIOV3UG4SPII3FQU2TGNNEK5TWMVRWW43BJZNDAQJLJ4VUGMDSKNWECZDZJNUVGRLYGRMTGKZVPF5HSM3VLBQXS4DDIQ4U2K3SKRCEKN3BKF2WYVDZJNDHSZKRNFNG4VKQNRBUUSDNLJBU45TLMZRFSUDJIVWHOOCGOFZGWULPJBEFKUBRMV2W4ODXIV2GO5RPOEVXSVCMMMVXATDMMRXUGT32HA2FIK3HO5YUCZRQIJYGI5CIIZSVCZLEHF4UC43ONBJDQN2LNVATG5BTKZ3WGZCUON3UE3KCMVREWVLPJBJHASKKLA3WYZTIHFXES4SPMI2VQMDFNRDCWZKDKN4DOVSSOJLUGQKKOM2E4ZZVHFGHS6LZG5THUUSDPF5DA5CTKBNE2N3FMJNHAV3FMR2E2TSYOJ3U46LTHBHESTBSIVKEEMDPKJVXAZDXM5FUCV3TLFCTKVLGINAUCQSVJAZXETLTKF3S63RVN5SW24RWHFFXU3LYMJAXEN3MPJIUKL3ZKVKESVCONM4S64LKNVFGCNSKG4ZEGULHLJUFU3KJIF4TC33GKVEDSMJWGUYDAMJTGZUWK4DJHBDFMUTKK5WEIOCJMFMGEVRZPFRFUWBVKZIHS2KHNY3HS43IGJUEWRRYIJMXO5KNM52UIK3LPFXWOULWO5MXOUDPN43HG2RLOREUSS3MGE2UU33WLFYWK3SNMZIFASLJNZLHU3CZIJ4WSSSTPA2VGY2XJN2XQZ2OMZBEO4RTK5LVUZLWGJZTCKZQKRZFMS2DKJTXE53NJ5BGERZPNFEXGSRYOFJXCNZZKZEDQZBYLJ5FSMZXI5CHITSFOA3UWQZUJF3FEMTRLF3U4ULGGRGTCTJQKBRHEZSSNJ4HITCONZSFCZZZJBZEY5BLHBSVCUCPNZNDSTLBMVUTIZLCGZQXMYK2KF2VSRRSMJSTKYKCJV4EUYJZIVLW4Z3KNRWUEULYJJKEC2SCM5VXC2DLNFDTS5ZQIJBVEVLYIZTVCVKFOBBFSNKWG5FVSUCGMFSXKZCMKJ4WGMSUJF5GY4BRGR3U2VCBNBGUC22HIJJXGT2BO5EWCQSRIFCUMQSQJVCWKZ2SJZ3XCWDTPFDWCTDSPJIG2TTSHFCHIU3NIJAWUUKUGNRDQZ3HIY2FOQKJINBUCQJ55YSI275KETZU3UUX4D2R4ACDMI"

    override fun start(context: Context, sessionId: String) {
        val clientInfo = TAS_CLIENT_INFO()
        clientInfo.vendorId = this.vendorId
        clientInfo.clientId = this.clientId
        clientInfo.clientKey = this.clientKey
        println(clientInfo.toString())

        val result = TasStart(context, clientInfo, TAS_INIT_NO_OPT, null, sessionId)
        if (result != TAS_RESULT_SUCCESS) {
            throw TrusteerOperationException(result)
        }
        println("Trusteer start status: Success");
    }

    override fun stop() {
        val result = TasStop()

        if (result != TAS_RESULT_SUCCESS) {
            throw TrusteerOperationException(result)
        }
    }

    override fun reset(sessionId: String) {
        val result = TasResetSession(sessionId)

        if (result != TAS_RESULT_SUCCESS) {
            throw TrusteerOperationException(result)
        }
    }
}

/**
 * Initialises a new [TrusteerOperationException] from a `TAS_RESULT_*` code from
 * [com.trusteer.tas.TasDefs].
 *
 * @param code A `TAS_RESULT_*` code from [com.trusteer.tas.TasDefs] to initialise the exception
 * with.
 *
 * Example usage:
 * ```
 * throw TrusteerOperationException(TAS_RESULT_NETWORK_ERROR)
 * ```
 */
class TrusteerOperationException(code: Int) : Exception(TrusteerOperationError.from(code).errorDescription)

/**
 * Defines the return value when an adaptive start or stop operation occurs.
 *
 * Represents the type of error for handling [AdaptiveContext.start] and [AdaptiveContext.stop]
 * operations.
 */
enum class TrusteerOperationError {
    /** A general error occurred during the collection process. */
    GENERAL_ERROR,
    /** An internal error occurred. Contact support. */
    INTERNAL_ERROR,
    /** The argument to initiate the collection process were incorrect. */
    INCORRECT_ARGUMENTS,
    /** The reference DRA item was not found. */
    NOT_FOUND,
    /** No polling has been configured. */
    NO_POLLING,
    /** Time out occurred. */
    TIME_OUT,
    /** The TAS collection process not initialized */
    NOT_INITIALIZED,
    /** Licence not authorized to perform operation. */
    LICENCE_NOT_AUTHORIZED,
    /** The TAS collection process already initialized. */
    ALREADY_INITIALIZED,
    /** Architecture not supported. */
    ARCHITECTURE_NOT_SUPPORTED,
    /** Incorrect TAS setup. */
    INCORRECT_SETUP,
    /** An internal exception occurred. Contact support. */
    INTERNAL_EXCEPTION,
    /** Insufficient permissions for collection process. */
    INSUFFICIENT_PERMISSIONS,
    /** Missing permission in tas folder or tas folder does not exist. */
    MISSING_PERMISSION_IN_FOLDER,
    /** TAS collection disabled due to configuration options. */
    DISABLED_BY_CONFIGURATION,
    /** A network error occurred. */
    NETWORK_ERROR,
    /** The internal connection timed out. Contact support. */
    INTERNAL_CONNECTION_TIMEOUT,
    /** Certificate error. Contact support. */
    CERTIFICATE_ERROR;

    companion object {
        /**
         * Initializes the [TrusteerOperationError] enum type from an [Int] representing a
         * `TAS_RESULT_*`.
         * @param value The integer of a `TAS_RESULT_*`.
         *
         * Example usage:
         * ```
         * val trusteereOperationError = TrusteerOperationError.from(TAS_RESULT_NETWORK_ERROR)
         * ```
         */
        fun from(value: Int): TrusteerOperationError {
            return when (value) {
                TAS_RESULT_GENERAL_ERROR -> GENERAL_ERROR
                TAS_RESULT_INTERNAL_ERROR -> INTERNAL_ERROR
                TAS_RESULT_WRONG_ARGUMENTS -> INCORRECT_ARGUMENTS
                TAS_RESULT_DRA_ITEM_NOT_FOUND -> NOT_FOUND
                TAS_RESULT_NO_POLLING -> NO_POLLING
                TAS_RESULT_TIMEOUT -> TIME_OUT
                TAS_RESULT_NOT_INITIALIZED -> NOT_INITIALIZED
                TAS_RESULT_UNAUTHORIZED -> LICENCE_NOT_AUTHORIZED
                TAS_RESULT_ALREADY_INITIALIZED -> ALREADY_INITIALIZED
                TAS_RESULT_ARCH_NOT_SUPPORTED -> ARCHITECTURE_NOT_SUPPORTED
                TAS_RESULT_INCORRECT_SETUP -> INCORRECT_SETUP
                TAS_RESULT_INTERNAL_EXCEPTION -> INTERNAL_EXCEPTION
                TAS_RESULT_INSUFFICIENT_PERMISSIONS -> INSUFFICIENT_PERMISSIONS
                TAS_RESULT_MISSING_PERMISSIONS_IN_FOLDER -> MISSING_PERMISSION_IN_FOLDER
                TAS_RESULT_DISABLED_BY_CONFIGURATION -> DISABLED_BY_CONFIGURATION
                TAS_RESULT_NETWORK_ERROR -> NETWORK_ERROR
                TAS_RESULT_CONNECTION_INTERNAL_TIMEOUT -> INTERNAL_CONNECTION_TIMEOUT
                TAS_RESULT_PINPOINT_CERTIFICATE_PROBLEM -> CERTIFICATE_ERROR
                else -> GENERAL_ERROR
            }
        }
    }
}

val TrusteerOperationError.errorDescription: String
    /**
     * Gets the error description of a [TrusteerOperationError].
     *
     * Example usage:
     * ```
     * TrusteerOperationError.NETWORK_ERROR.errorDescription // Returns "A network error occurred."
     * ```
     */
    get() {
        return when(this) {
            TrusteerOperationError.GENERAL_ERROR -> "A general error occurred during the collection process."
            TrusteerOperationError.INTERNAL_ERROR,
            TrusteerOperationError.INTERNAL_EXCEPTION -> "An internal error occurred. Contact support."
            TrusteerOperationError.INCORRECT_ARGUMENTS -> "The argument to initiate the collection process were incorrect."
            TrusteerOperationError.NOT_FOUND -> "The reference DRA item was not found."
            TrusteerOperationError.NO_POLLING -> "No polling has been configured."
            TrusteerOperationError.TIME_OUT,
            TrusteerOperationError.INTERNAL_CONNECTION_TIMEOUT -> "Time out occurred."
            TrusteerOperationError.NOT_INITIALIZED -> "The TAS collection process not initialized."
            TrusteerOperationError.LICENCE_NOT_AUTHORIZED -> "Licence not authorized to perform operation."
            TrusteerOperationError.ALREADY_INITIALIZED -> "The TAS collection process already initialized."
            TrusteerOperationError.ARCHITECTURE_NOT_SUPPORTED -> "Architecture not supported."
            TrusteerOperationError.INCORRECT_SETUP -> "Incorrect TAS setup."
            TrusteerOperationError.INSUFFICIENT_PERMISSIONS -> "Insufficient permissions for collection process."
            TrusteerOperationError.MISSING_PERMISSION_IN_FOLDER -> "Missing permission in tas folder or tas folder does not exist."
            TrusteerOperationError.DISABLED_BY_CONFIGURATION -> "TAS collection disabled due to configuration options."
            TrusteerOperationError.NETWORK_ERROR -> "A network error occurred."
            TrusteerOperationError.CERTIFICATE_ERROR -> "Certificate error. Contact support."
        }
    }