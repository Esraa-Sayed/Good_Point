from django.db import models
from user_account.models import User


# Create your models here.

class LostObject(models.Model):
    date = models.DateTimeField(null=False)
    city = models.CharField(max_length=35,null=False)
    user_id = models.ForeignKey(User,related_name='lost')


class LostPerson(models.Model):
    id_l = models.ForeignKey(LostObject,primary_key=True)
    image = models.BinaryField(primary_key=True,unique=True)


class LostItem(models.Model):
    id_l = models.ForeignKey(LostObject, primary_key=True)
    type = models.CharField(max_length=20)
    color = models.CharField(max_length=20, null=True)
    brand = models.CharField(max_length=50, null=True)
    description = models.CharField(max_length=700)
    serial_number = models.CharField(max_length=100,null=True)


class FoundObject(models.Model):
    date = models.DateTimeField(null=False)
    longitude = models.DecimalField()
    latitude = models.DecimalField()
    city = models.CharField(max_length=35,null=False)
    is_delivered = models.BooleanField()
    user_id = models.ForeignKey(User, related_name='lost')


class FoundPerson(models.Model):
    id_f = models.ForeignKey(LostObject, primary_key=True)
    image = models.BinaryField(primary_key=True, unique=True)


class FoundItem(models.Model):
    id_f = models.ForeignKey(LostObject, primary_key=True)
    type = models.CharField(max_length=20)
    color = models.CharField(max_length=20, null=True)
    brand = models.CharField(max_length=50, null=True)
    description = models.CharField(max_length=700)
    serial_number = models.CharField(max_length=100, null=True)


class Match(models.Model):
    id_l = models.ForeignKey(LostObject, primary_key=True, on_delete=models.CASCADE(), null=False)
    id_f = models.ForeignKey(LostObject, primary_key=True, on_delete=models.CASCADE(), null=False)
    date_of_receiving = models.DateTimeField(auto_now_add=True)


class Candidate(models.Model):
    id_l = models.ForeignKey(LostObject, primary_key=True, null=False)
    id_f = models.ForeignKey(LostObject, primary_key=True, null=False)