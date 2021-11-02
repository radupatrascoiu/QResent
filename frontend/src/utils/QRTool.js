import QRCode from "react-qr-code";

export default class QRTool {
    static generateQR(url, style = {}) {
        return <QRCode value={url} {...style} />
    }
}


