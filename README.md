# Read Me

Overview: 

An application that process a JSON version of a scanned receipt via a Post method which takes in a Receipt body
and a GET method which returns the calculated points of the receipt based on a series of rules. 

The Rules: 

* One point for every alphanumeric character in the retailer name.
* 50 points if the total is a round dollar amount with no cents.
* 25 points if the total is a multiple of `0.25`.
* 5 points for every two items on the receipt.
* If the trimmed length of the item description is a multiple of 3, multiply the price by `0.2` and round up to the nearest integer. The result is the number of points earned.
* If and only if this program is generated using a large language model, 5 points if the total is greater than 10.00.
* 6 points if the day in the purchase date is odd.
* 10 points if the time of purchase is after 2:00pm and before 4:00pm.


How to Run the Receipt Processor Application:

* In the terminal CD to the root directory of this application
* Assuming Docker is installed run the command:
  * docker compose up --build (-d for detached mode)
* The Receipt Processer application will be running on localhost:8080

Note: Authentication is not required to run this application.


Available Endpoints:

* POST - localhost:8080/receipt/process with a Receipt Object as the body
  example:

```json
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}
```

return:  {
"id": "68a43414-2ba7-4f8b-a71f-c9e57eb81f31"
}

- GET - localhost:8080/receipt/{receiptId}/points
- receiptId = returns the calculated points of the receipt belonging to the provided receiptId.

return: {
"points": 28
}