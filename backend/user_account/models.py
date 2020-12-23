from django.db import models


# Create your models here.

class User(models.Model):
    email = models.EmailField(unique=True, null=False, )
    password = models.CharField(max_length=128, null=False)
    name = models.CharField(max_length=150, null=False)
    phone = models.CharField(max_length=20, null=False, unique=True)
    birthdate = models.DateField(null=False)
    city = models.CharField(max_length=35, null=False)
    profile_pic = models.BinaryField(blank=True, null=True)
    id_card_pic = models.BinaryField(blank=True, null=True)
