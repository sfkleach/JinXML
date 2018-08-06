```javascript
{"widget": {
    "debug": "on",
    "window": {
        "title": "Sample Konfabulator Widget",
        "name": "main_window",
        "width": 500,
        "height": 500
    },
    "image": { 
        "src": "Images/Sun.png",
        "name": "sun1",
        "hOffset": 250,
        "vOffset": 250,
        "alignment": "center"
    },
    "text": {
        "data": "Click Here",
        "size": 36,
        "style": "bold",
        "name": "text1",
        "hOffset": 250,
        "vOffset": 100,
        "alignment": "center",
        "onMouseUp": "sun1.opacity = (sun1.opacity / 100) * 90;"
    }
}}    
```

The same might be represented like this in JinXML:
```
<widget debug="on">
    <window>
        title: "Sample Konfabulator Widget",
        name: "main_window",
        width: 500,
        height: 500
    </window>
    <image>
        src: "Images/Sun.png",
        name: "sun1",
        hOffset: 250,
        vOffset: 250,
        alignment: "center"
    </image>
    <text>
        data: "Click Here",
        size": 36,
        style: "bold",
        name: "text1",
        hOffset: 250,
        vOffset: 100,
        alignment: "center",
        onMouseUp: "sun1.opacity = (sun1.opacity / 100) * 90;"
    </text>
</widget>
```
