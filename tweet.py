import twitter


class Status:
    def __init__(self, text, hashtag):
        self.text = text
        self.hashtag = hashtag


api = twitter.Api(consumer_key='7We9xx7DWcUEppMefz63XTaKR',
                  consumer_secret='Ndb3mGqaz3xBP403WFkskOmyGcOEjKHRDrp8psOzHB702tqHGt',
                  access_token_key='2498042509-w8rt9D3Y82FVe1lFAOGXgh56jGCqCz7nJ6ud3Lo',
                  access_token_secret='SbXFat0KuMxp194eupyor8hrRqgGUdXlAngqF4AO2c5YD')
