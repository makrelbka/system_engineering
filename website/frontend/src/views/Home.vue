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
import { ref, inject } from 'vue'
import { useToast } from '../composables/useToast'
import Header from '../components/Header.vue'
import HeroTitle from '../components/HeroTitle.vue'
import CallbackForm from '../components/CallbackForm.vue'
import ProductGrid from '../components/ProductGrid.vue'
import Footer from '../components/Footer.vue'
import Toaster from '../components/Toaster.vue'

const { toast } = useToast()

// Получаем корзину через inject
const cart = inject('cart')

const products = ref([
  {
    id: 1,
    name: "Розовые розы",
    price: 45.99,
    image: "https://images.unsplash.com/photo-1716982360804-b0bfdb28103e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwaW5rJTIwcm9zZXMlMjBib3VxdWV0fGVufDF8fHx8MTc2ODM3MTA3MHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "Элегантный букет свежих розовых роз",
  },
  {
    id: 2,
    name: "Подсолнухи",
    price: 39.99,
    image: "https://images.unsplash.com/photo-1629386255808-c3ceb405173c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzdW5mbG93ZXIlMjBib3VxdWV0fGVufDF8fHx8MTc2ODMwNjMwMHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "Яркая и жизнерадостная композиция",
  },
  {
    id: 3,
    name: "Тюльпаны микс",
    price: 42.99,
    image: "https://images.unsplash.com/photo-1556291474-7ded8a3b2d38?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0dWxpcHMlMjBjb2xvcmZ1bHxlbnwxfHx8fDE3Njg0MDM0NDV8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "Разноцветные свежие тюльпаны",
  },
  {
    id: 4,
    name: "Лаванда",
    price: 34.99,
    image: "https://images.unsplash.com/photo-1541927634837-a7d5c4892527?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsYXZlbmRlciUyMGZsb3dlcnN8ZW58MXx8fHwxNzY4MzcyOTI4fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "Ароматная лаванда для релаксации",
  },
  {
    id: 5,
    name: "Белые орхидеи",
    price: 59.99,
    image: "https://images.unsplash.com/photo-1710524784485-5c77ae822ecc?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3aGl0ZSUyMG9yY2hpZHxlbnwxfHx8fDE3Njg0MDM0NDZ8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "Экзотические орхидеи в горшке",
  },
  {
    id: 6,
    name: "Букет из лилий",
    price: 48.99,
    image: "https://images.unsplash.com/photo-1712258090342-f31b387221a3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsaWx5JTIwZmxvd2VycyUyMGJvdXF1ZXR8ZW58MXx8fHwxNzY4NDAzNDQ2fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "Роскошная композиция из свежих лилий",
  },
  {
    id: 7,
    name: "Розовые пионы",
    price: 54.99,
    image: "https://images.unsplash.com/photo-1588457776180-4206b4909301?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZW9ueSUyMGZsb3dlcnMlMjBwaW5rfGVufDF8fHx8MTc2ODMzNzgzN3ww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "Роскошный букет розовых пионов",
  },
  {
    id: 8,
    name: "Белые ромашки",
    price: 32.99,
    image: "https://images.unsplash.com/photo-1618929990290-e32fa75925be?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxkYWlzeSUyMGZsb3dlcnMlMjB3aGl0ZXxlbnwxfHx8fDE3Njg0MDM0NDd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
    description: "Простые и элегантные белые ромашки",
  },
])

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
