from django.db import models
from django.contrib.auth.models import AbstractUser


# Create your models here.

class User(AbstractUser):
    phone = models.CharField(max_length=20, null=False, unique=True)
    birthdate = models.DateField(null=False)
    city = models.CharField(max_length=35, null=False)
    profile_pic = models.BinaryField(blank=True, null=True)
    id_card_pic = models.BinaryField(blank=True, null=True)
