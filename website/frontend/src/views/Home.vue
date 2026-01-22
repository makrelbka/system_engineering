<template>
  <div class="min-h-screen bg-white">
    <Header
      :cart="cart"
      @remove-from-cart="handleRemoveFromCart"
      @update-quantity="handleUpdateQuantity"
    />
    <HeroTitle />
    <CallbackForm />
    <ProductGrid :products="products" @add-to-cart="handleAddToCart" />
    <Footer />
    <Toaster />
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue'
import { useToast } from '../composables/useToast'
import Header from '../components/Header.vue'
import HeroTitle from '../components/HeroTitle.vue'
import CallbackForm from '../components/CallbackForm.vue'
import ProductGrid from '../components/ProductGrid.vue'
import Footer from '../components/Footer.vue'
import Toaster from '../components/Toaster.vue'

const { toast } = useToast()

const cart = inject('cart')

const products = ref([])
const apiUrl = import.meta.env.VITE_API_URL || '/api'

const loadProducts = async () => {
  try {
    const response = await fetch(`${apiUrl}/products`)
    if (!response.ok) throw new Error('Не удалось загрузить каталог')
    const data = await response.json()
    products.value = data || []
  } catch (e) {
    toast.error(e.message || 'Ошибка загрузки каталога')
  }
}

onMounted(() => {
  loadProducts()
})

const handleAddToCart = (product) => {
  const existingItem = cart.value.find((item) => item.id === product.id)
  if (existingItem) {
    existingItem.quantity += 1
  } else {
    cart.value.push({ ...product, quantity: 1 })
  }
}

const handleRemoveFromCart = (id) => {
  cart.value = cart.value.filter((item) => item.id !== id)
  toast.success("Товар удален из корзины")
}

const handleUpdateQuantity = (id, quantity) => {
  if (quantity === 0) {
    handleRemoveFromCart(id)
  } else {
    const item = cart.value.find((item) => item.id === id)
    if (item) {
      item.quantity = quantity
    }
  }
}
</script>
