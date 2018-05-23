package trickyquestion.messenger.buisness

import android.content.Context
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector

open class P2PConnector(private val context: Context) {
    open fun connect() { P2PProtocolConnector.tryStart(context)  }
}