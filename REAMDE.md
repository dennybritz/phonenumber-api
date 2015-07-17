This API uses Google's [libphonenumber](https://github.com/googlei18n/libphonenumber) to extract phone numbers from raw text.

Usage:


```bash
docker-compose up -d
curl -X POST http://localhost:14001/findNumbers -H "Content-Type: application/json" -d '
  {
    "text": "Call me at 425 882-8080 for details."
  }
'
```

Response:

```JSON
[{
  "formatInternational": "+1 425-882-8080",
  "formatE164": "+14258828080",
  "nationalNumber": 4258828080,
  "raw": "425 882-8080",
  "countryCode": 1,
  "formatNational": "(425) 882-8080",
  "extension": "",
  "formatRFC3966": "tel:+1-425-882-8080"
}]
```

Have fun.
